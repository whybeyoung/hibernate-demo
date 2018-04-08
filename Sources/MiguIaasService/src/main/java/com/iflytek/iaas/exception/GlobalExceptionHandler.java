/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: GlobalExceptionHandler
 * Author:   xwliu
 * Date:     2018/3/30 17:08
 * Description: 全局异常处理
 */
package com.iflytek.iaas.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈全局异常处理〉
 *
 * @author xwliu
 * @create 2018/3/30
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 Exception 类型的异常
      * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,Object> exceptionHander(Exception e){

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code","12324324");
        map.put("message","testtesttest");
        return map;
    }
}
