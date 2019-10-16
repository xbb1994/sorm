package com.nt.sorm.utils;

import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Pattern;


public class StringUtil {

    public static boolean isBlank(String str) {
        boolean result = false;
        if (str == null || "".equals(str)) {
            result = true;
        }
        return result;
    }

    public static boolean isNotBlank(String str) {
        System.out.println("come in isNotBlank!********************************");
        boolean result = true;
        if (str == null || "".equals(str)) {
            result = false;
        }
        return result;
    }


    /** 
    * @Description:  图片转化成base64字符串
    * @Param: [path] 
    * @return: java.lang.String 
    * @Author: Xu 
    * @Date: 2019/5/27 
    */ 
    public static String getImageStr(String path) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        // 待处理的图片
        String imgFile = path;
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        return Base64.getEncoder().encodeToString(data);
//		return Base64.encodeBytes(data, Base64.DONT_BREAK_LINES);// 返回Base64编码过的字节数组字符串
    }

    public static String getBase64Head(String type) {
        StringBuffer sb = new StringBuffer();
        sb.append("data:");
        sb.append(type);
        sb.append(";base64,");
        return sb.toString();
    }

    /****
     * 将含有HTML标签的文本转换为纯文本文件
     *
     * @param inputString
     * @return
     */
    public static String html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }


    /**
     * MD5加密算法
     *
     * @param str 需要转化为MD5码的字符串
     * @return 返回一个32位16进制的字符串
     */
    public static String toMd5(String str) {
        StringBuffer md5Code = null;
        try {
            //获取加密方式为md5的算法对象
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(str.getBytes());
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 0xff);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                md5Code = md5Code.append(b);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Code.toString();

    }

    /** 
    * @Description: MD5 
    * @Param: [str] 
    * @return: java.lang.String 
    * @Author: Xu 
    * @Date: 2019/1/23 
    */ 
    public static String stringToMd5(String str) {
        try {
            //确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
            return newstr;
        } catch (Exception e) {
            e.printStackTrace();
            return  str;
        }

    }


    /* * Java文件操作 获取文件扩展名 * * Created on: 2011-8-2 * Author: blueeagle */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /* * Java文件操作 获取不带扩展名的文件名 * * Created on: 2011-8-2 * Author: blueeagle */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


}
