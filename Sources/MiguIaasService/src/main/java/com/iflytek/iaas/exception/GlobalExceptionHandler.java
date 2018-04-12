/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: GlobalExceptionHandler
 * Author:   xwliu
 * Date:     2018/3/30 17:08
 * Description: 全局异常处理
 */
package com.iflytek.iaas.exception;

import com.iflytek.iaas.dto.ErrorResponse;
import com.iflytek.iaas.exception.ControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
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
    public Map<String,Object> exceptionHander(ControllerException e){

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",e.getCode());
        map.put("message",e.getMessage());
        return map;
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(MiguForbiddenException.class)
    @ResponseBody
    public ErrorResponse handleCustomException(HttpServletRequest req, Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
