/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: MD5Utils
 */
package com.iflytek.iaas.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 〈md5相关工具类〉
 *
 * @author xwliu
 * @create 2018/4/3
 */
public class MD5Utils {
    /**
     * 密码加密
     * @param pwd 密码
     * @param salt 盐值
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encrypt(String pwd,String salt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        // 使用指定的字节更新摘要
        messageDigest.update(pwd.getBytes());
        messageDigest.update(salt.getBytes());
        // 获得密文
        byte[] result = messageDigest.digest();
        // 把密文转换成十六进制的字符串形式
        return bytes2HexString(result);
    }

    /**
     * 把字节数组转换为16进制的形式
     *
     * @param b
     * @return
     */
    public static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * MD5 32位加密大写
     *
     * @param sourceStr
     * @return
     */
    public static String MD5To32UpperCase(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result.toUpperCase();
    }

    public static void main(String[] args) throws IOException {
        try {
            String pwd = encrypt("123456","iflytek");
            System.out.print(pwd);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
