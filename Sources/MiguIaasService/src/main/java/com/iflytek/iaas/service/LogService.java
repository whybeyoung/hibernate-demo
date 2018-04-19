/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: LogService
 */
package com.iflytek.iaas.service;

import com.iflytek.iaas.consts.LogType;
import com.iflytek.iaas.dto.OperationLogDTO;
import org.springframework.data.domain.Page;


/**
 * 〈业务日志服务〉
 *
 * @author xwliu
 * @create 2018/4/18
 */
public interface LogService {

    /**
     * 保存操作日志信息
     * @param operationLogDTO 日志dto
     * @return
     */
    Boolean saveOperationLog(OperationLogDTO operationLogDTO);

    /**
     * 分页获取操作日志信息
     * @param pageIndex 页码,从1开始
     * @param pageSize 页大小，最大100
     * @return
     */
    Page<OperationLogDTO> findOperationLog(Integer pageIndex,Integer pageSize);

    /**
     * 根据创建者分页获取操作日志
     * @param creator 创建者
     * @param pageIndex 页码,从1开始
     * @param pageSize 页大小，最大100
     * @return
     */
    Page<OperationLogDTO> findOperationLogByCreator(String creator,Integer pageIndex,Integer pageSize);

    /**
     * 根据操作日志类型分页获取操作日志
     * @param type 操作类型
     * @param pageIndex 页码,从1开始
     * @param pageSize 页大小，最大100
     * @return
     */
    Page<OperationLogDTO> findOperationLogByType(LogType type, Integer pageIndex, Integer pageSize);

    /**
     * 根据操作日志类型和创建者分页获取操作日志
     * @param type 操作日志类型
     * @param creator 创建者
     * @param pageIndex  页码,从1开始
     * @param pageSize 页大小，最大100
     * @return
     */
    Page<OperationLogDTO> findOperationLogByTypeAndCreator(LogType type,String creator, Integer pageIndex, Integer pageSize);

}
