/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ControllerException
 */
package com.iflytek.iaas.exception;

import com.iflytek.iaas.consts.ReturnCode;

/**
 * 〈controller异常〉
 *
 * @author xwliu
 * @create 2018/4/9
 */
public class ControllerException extends Exception  {

    private static final long serialVersionUID = -3861486259618838450L;

    private ReturnCode returnCode;

    public ControllerException(){
        super();
    }
    public ControllerException(String msg){
        super(msg);
    }

    public  ControllerException(ReturnCode returnCode){
        this.returnCode = returnCode;
    }

    @Override
    public String getMessage() {
        return this.returnCode.getMsg();
    }

    public String getCode(){
        return this.returnCode.getCode();

    }
}
