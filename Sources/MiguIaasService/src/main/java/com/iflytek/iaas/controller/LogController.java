/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: LogController
 */
package com.iflytek.iaas.controller;

import com.alibaba.fastjson.JSON;
import com.iflytek.iaas.consts.LogType;
import com.iflytek.iaas.consts.ReturnCode;
import com.iflytek.iaas.dto.OperationLogDTO;
import com.iflytek.iaas.exception.ControllerException;
import com.iflytek.iaas.service.LogService;
import jdk.internal.org.objectweb.asm.signature.SignatureWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈日志管理模块controller〉
 *
 * @author xwliu
 * @create 2018/4/19
 */
@RestController
@RequestMapping(path="api/v1")
public class LogController {

    Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    /**
     * 分页获取操作日志列表
     * @param creator 创建者
     * @param type 日志类型
     * @param index 页数，从1开始
     * @param size 页大小
     * @return
     * @throws ControllerException
     */
    @GetMapping("/operationlogs")
    public String getOperationLosg(String creator,String type,String index, String size)throws ControllerException{
        if(StringUtils.isEmpty(index) || StringUtils.isEmpty(size)){
            throw new ControllerException(ReturnCode.PARAM_UNVALID);
        }
        LogType logType=null;
        switch (type){
            case "0":
                logType = LogType.NEW_DEPLOY;
            case "1":
                logType = LogType.OFFLINE;
            case "2":
                logType = LogType.SCALE;
            case "3":
                logType = LogType.DELETE;
        }
        Page<OperationLogDTO> operationLogs;
        if(StringUtils.isEmpty(creator) && StringUtils.isEmpty(type)){
            operationLogs = logService.findOperationLog(Integer.valueOf(index),Integer.valueOf(size));
        }else if(StringUtils.isNotEmpty(creator)){
            operationLogs = logService.findOperationLogByCreator(creator,Integer.valueOf(index),Integer.valueOf(size));
        }else if(StringUtils.isNotEmpty(type)){
            operationLogs = logService.findOperationLogByType(logType,Integer.valueOf(index),Integer.valueOf(size));
        }else{
            operationLogs = logService.findOperationLogByTypeAndCreator(logType,creator,Integer.valueOf(index),Integer.valueOf(size));
        }
        return JSON.toJSONString(operationLogs);
    }
}
