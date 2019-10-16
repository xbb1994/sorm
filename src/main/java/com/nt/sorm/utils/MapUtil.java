package com.nt.sorm.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtil<K, V> extends HashMap<K, V> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // 重写HashMapSon类的toString()方法
    @Override
    public String toString() {
        Iterator<Entry<K, V>> i = entrySet().iterator();
        if (!i.hasNext()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Entry<K, V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key == this ? "(this Map)" : key);
            sb.append('=');
            //sb.append(value == this ? "(this Map)" :value);
            //重写了该处的方法，递归调用该方法。使tostring方法里面的数都是string类型
            if (value == this) {
                sb.append("(this Map)");
            } else {
                if (value instanceof Map) {
                    @SuppressWarnings("rawtypes")
                    MapUtil valuestr = (MapUtil) value;
                    sb.append(valuestr.toString());
                } else {
                    sb.append(value);
                }
            }

            if (!i.hasNext()) {
                return sb.append('}').toString();
            }
            sb.append(',').append(' ');
        }
    }

    //首先是将map和要被赋值的Bean传进来
    public static void setValue(Map map, Object thisObj) {
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            Object val = map.get(obj);
            setMethod(obj, val, thisObj);
        }
    }


    //调用设值方法setMethod方法（暂时只支持传入String类型字段的处理）
    public static void setMethod(Object method, Object value, Object thisObj) {
        Class c;
        try {
            c = Class.forName(thisObj.getClass().getName());
            String met = (String) method;
            met = met.trim();
            if (!met.substring(0, 1).equals(met.substring(0, 1).toUpperCase())) {
                met = met.substring(0, 1).toUpperCase() + met.substring(1);
            }
            if (!String.valueOf(method).startsWith("set")) {
                met = "set" + met;
            }
            Class types[] = new Class[1];
            types[0] = Class.forName("java.lang.String");
            Method m = c.getMethod(met, types);
            m.invoke(thisObj, value);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    //下面是把Bean转换成map对象输出
    public static Map getValue(Object thisObj) {
        Map map = new HashMap();
        Class c;
        try {
            c = Class.forName(thisObj.getClass().getName());
            Method[] m = c.getMethods();
            for (int i = 0; i < m.length; i++) {
                String method = m[i].getName();
                if (method.startsWith("get")) {
                    try {
                        Object value = m[i].invoke(thisObj);
                        if (value != null) {
                            String key = method.substring(3);
                            key = key.substring(0, 1).toUpperCase() + key.substring(1);
                            map.put(method, value);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("error:" + method);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return map;
    }


}
