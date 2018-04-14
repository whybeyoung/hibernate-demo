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
public class BusiException extends RuntimeException  {


    private static final long serialVersionUID = -5497798973460430098L;
    private ReturnCode returnCode;

    public BusiException(){
        super();
    }
    public BusiException(String msg){
        super(msg);
    }

    public BusiException(ReturnCode returnCode){
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
