/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ToolUtils
 */
package com.iflytek.iaas.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 〈常用工具类〉
 *
 * @author xwliu
 * @create 2018/4/3
 */
public class ToolUtils {
    /**
     * 生成UUID
     * @return
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-",	"");
    }

    /**
     * 获取唯一ID，时间戳+六位随机数
     * @return
     */
    public static String getUniqueId(){
        Random random = new Random();
        return String.valueOf(System.currentTimeMillis())+String.valueOf(random.nextInt(900000)+100000);
    }

    /**
     * 获取密码加密盐值
     * @return
     */
    public static String getSalt(){
        String salt="";
        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] chars = str.toCharArray();
        Random random = new Random();
        for(int i=0;i<4;i++){
            salt += chars[random.nextInt(61)];
        }
        return salt;
    }
}
