package com.nt.sorm.dao;

import com.nt.sorm.annotation.PK;
import com.nt.sorm.annotation.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: dao基类
 * @author: Xu
 * @create: 2019-05-09 16:43
 **/
public class BaseDao<T> {
    @Autowired
    JdbcTemplate jdbcTemplate;





    /**
     * @Description: 插入数据
     * @Param: [t]
     * @return: int
     * @Author: Xu
     * @Date: 2019/5/10
     */
    protected int insert(T t) {
        Class clazz = t.getClass();
        String tableName = getTableName(clazz);
        Field[] fields = getFieldArray(clazz);
        StringBuilder sql = new StringBuilder("insert into " + tableName);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        List<Object> params = new ArrayList<>();
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                columns.append(f.getName().toLowerCase() + ",");
                values.append("?,");
                params.add(f.get(t));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        columns.setCharAt(columns.length() - 1, ')');
        values.setCharAt(values.length() - 1, ')');
        sql.append(" " + columns.toString());
        sql.append(" values " + values.toString());
        System.out.println("Insert SQL:" + sql.toString());
        System.out.println(params.toArray());
        return jdbcTemplate.update(sql.toString(), params.toArray());
    }


    /**
     * @Description: 根据主键删除
     * @Param: [t, guid]
     * @return: void
     * @Author: Xu
     * @Date: 2019/5/10
     */
    protected void deleteByGuid(T t, String pkValue) {
        Class clazz = t.getClass();
        String tableName = getTableName(clazz);
        Field[] fields = clazz.getDeclaredFields();
        //设置默认的主键名为 rowguid
        String pkName = "rowguid";
        //获取带有主键注解的字段名
        for (Field field : fields) {
            field.setAccessible(true);
            if (!StringUtils.isEmpty(field.getAnnotation(PK.class))) {
                pkName = field.getName();
            }
        }
        if (!StringUtils.isEmpty(pkValue)) {
            String sql = "delete from " + tableName + " where " + pkName + " = '" + pkValue + "'";
            System.out.println("Delete SQL:" + sql);
            jdbcTemplate.update(sql);
        } else {
            throw new RuntimeException("Error:The Guid is null when excute deleteByGuid method!");
        }

    }


    /**
     * @Description: 更新一条记录
     * @Param: [t]
     * @return: int
     * @Author: Xu
     * @Date: 2019/5/13
     */
    protected int update(T t) {
        Class clazz = t.getClass();
        String tableName = getTableName(clazz);
        Field[] fields = getFieldArray(clazz);
        StringBuilder sql = new StringBuilder("update " + tableName + " set ");
        StringBuilder columns = new StringBuilder(" ");
        List<Object> params = new ArrayList<>();
        try {
            String pkName = "rowguid";
            Object pkValue = null;
            for (Field field : fields) {
                field.setAccessible(true);
                if (!StringUtils.isEmpty(field.getAnnotation(PK.class))) {
                    pkName = field.getName();
                    pkValue = field.get(t);
                } else {
                    columns.append(field.getName() + " = ?,");
                    params.add(field.get(t));
                }
            }
            columns.setCharAt(columns.length() - 1, ' ');
            sql.append(columns.toString() + " where " + pkName + " = ?");
            params.add(pkValue);
            System.out.println("Update SQL:" + sql.toString());
            return jdbcTemplate.update(sql.toString(), params.toArray());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }

    }


    /**
     * @Description: 根据主键查询单条记录
     * @Param: [clazz, pkValue]
     * @return: T
     * @Author: Xu
     * @Date: 2019/5/14
     */
    public T find(Class clazz, Object pkValue) {
        String tableName = getTableName(clazz);
        Field[] fields = getFieldArray(clazz);
        String pkName = "rowguid";
        for (Field field : fields) {
            field.setAccessible(true);
            if (!StringUtils.isEmpty(field.getAnnotation(PK.class))) {
                pkName = field.getName();
            }
        }
        String sql = "select * from " + tableName + " where " + pkName + " = ?";
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);
        return jdbcTemplate.queryForObject(sql, new Object[]{pkValue}, rowMapper);
    }

    /** 
    * @Description: 根据sql，获取第一条记录 
    * @Param: [clazz, sql, args] 
    * @return: T 
    * @Author: Xu 
    * @Date: 2019/5/23 
    */ 
    public T find(Class clazz, String sql, Object... args) {
        List<T> list = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(clazz));
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    
    /** 
    * @Description: 根据sql，获取数据总量 
    * @Param: [clazz, sql, args] 
    * @return: int 
    * @Author: Xu 
    * @Date: 2019/5/23 
    */ 
    public int getCount(Class clazz, String sql,Object... args){
        return jdbcTemplate.queryForObject(sql,args,Integer.class);
    }


    /** 
    * @Description: 根据sql，获取list 
    * @Param: [clazz, sql, sorts, args] 
    * @return: java.util.List<T> 
    * @Author: Xu 
    * @Date: 2019/5/23 
    */ 
    public List<T> findList(Class clazz, String sql, String sorts, Object... args) {
        if (!StringUtils.isEmpty(sorts)) {
            sql += (" order by " + sorts);
        }
        return  jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(clazz));

    }

    /** 
    * @Description: 分页查询
    * @Param: [clazz, sql, sorts, pageIndex, pageSize, args] 
    * @return: java.util.List<T> 
    * @Author: Xu 
    * @Date: 2019/5/23 
    */ 
    public List<T> findList(Class clazz, String sql, String sorts, int pageIndex, int pageSize, Object... args) {
        if (!StringUtils.isEmpty(sorts)) {
            sql += (" order by " + sorts);
        }
        sql += "limit "+pageSize*(pageIndex-1) + "," + pageSize;
        return  jdbcTemplate.query(sql, args, new BeanPropertyRowMapper(clazz));
    }


    /**
     * @Description: 获取类的字段fields的数组形式
     * @Param: [clazz]
     * @return: java.lang.reflect.Field[]
     * @Author: Xu
     * @Date: 2019/5/10
     */
    public static Field[] getFieldArray(Class clazz) {
        //TO DO 从缓存中，根据class取出fields,没有即通过映射方法获取,并添加到缓存中
        Field[] declaredFields = clazz.getDeclaredFields();
        return declaredFields;
    }


    /**
     * @Description: 获取类的字段fields数组转化之后list
     * @Param: [clazz]
     * @return: java.util.List<java.lang.reflect.Field>
     * @Author: Xu
     * @Date: 2019/5/10
     */
    public static List<Field> getFieldList(Class clazz) {
        // 获取所有字段
        Field[] fieldsArray = getFieldArray(clazz);
        return Arrays.asList(fieldsArray);
    }

    /**
     * @Description: 不使用注解，直接通过反射获取表名
     * @Param: [clazz]
     * @return: java.lang.String
     * @Author: Xu
     * @Date: 2019/5/9
     */
    public static String getTableName(Class clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    /**
     * @Description: 根据类的字段list，获取所有的列名的list
     * @Param: [fields]
     * @return: java.util.List<java.lang.String>
     * @Author: Xu
     * @Date: 2019/5/10
     */
    public static List<String> getColumnList(List<Field> fields) {
        List<String> columnsList = new ArrayList<>();
        for (Field field : fields) {
            columnsList.add(field.getName());
        }
        return columnsList;
    }


    /**
     * @Description: 通过注解的方式获取表名，如果没有注解，则直接获取类名
     * @Param: [clazz]
     * @return: java.lang.String
     * @Author: Xu
     * @Date: 2019/5/10
     */
    public static String getTableNameWithAnnotation(Class clazz) {
        Table tableAnnotation = clazz.getClass().getAnnotation(Table.class);
        if (tableAnnotation != null) {
            return tableAnnotation.value();
        } else {
            return clazz.getSimpleName().toLowerCase();
        }
    }

    /**
     * @Description: 获取字段对应的名字
     * @Param: [field]
     * @return: java.lang.String
     * @Author: Xu
     * @Date: 2019/5/10
     */
    public static String getColumnName(Field field) {
        return field.getName().toLowerCase();
    }


}
