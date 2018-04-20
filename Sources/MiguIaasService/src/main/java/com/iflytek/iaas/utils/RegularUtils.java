/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: RegularUtils
 */
package com.iflytek.iaas.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 〈正则工具〉
 *
 * @author xwliu
 * @create 2018/4/8
 */
public class RegularUtils {

    /**
     * 判断是否是手机号
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        String pat = "^1[3|4|5|7|8][0-9]{9}$";
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否是邮箱地址
     * @param str
     * @return
     */
    public static boolean isEmail(String str){
        String pat = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否满足k8s命名
     * @param str
     * @return
     */
    public static boolean isK8sAllowedName(String str){
        String pat = "[a-z0-9]([-a-z0-9]*[a-z0-9])?";
        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
