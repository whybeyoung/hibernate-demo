/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: LogServiceImpl
 */
package com.iflytek.iaas.service.impl;

import com.iflytek.iaas.consts.LogType;
import com.iflytek.iaas.dao.OperationLogDao;
import com.iflytek.iaas.domain.OperationLog;
import com.iflytek.iaas.dto.OperationLogDTO;
import com.iflytek.iaas.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 〈日志服务〉
 *
 * @author xwliu
 * @create 2018/4/18
 */
@Service("LogService")
public class LogServiceImpl implements LogService{

    private Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public Boolean saveOperationLog(OperationLogDTO operationLogDTO) {
        OperationLog operationLog = new OperationLog();
        BeanUtils.copyProperties(operationLogDTO,operationLog);
        try{
            operationLog.setCreatetime(new Date());
            OperationLog log = operationLogDao.save(operationLog);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Page<OperationLogDTO> findOperationLog(Integer pageIndex, Integer pageSize) {
        try{
            pageSize = pageSize > 100?100:pageSize;
            Sort sort = new Sort(Sort.Direction.DESC, "createtime");
            Page<OperationLog> operationLogs = operationLogDao.findAll(PageRequest.of(pageIndex.intValue()-1,pageSize.intValue(),sort));
            Page<OperationLogDTO> operationLogList = operationLogs.map((item)->{
                OperationLogDTO operationLogDTO = new OperationLogDTO();
                BeanUtils.copyProperties(item,operationLogDTO);
                return operationLogDTO;
            });
            return operationLogList;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Page<OperationLogDTO> findOperationLogByCreator(String creator, Integer pageIndex, Integer pageSize) {
        try{
            pageSize = pageSize > 100?100:pageSize;
            Sort sort = new Sort(Sort.Direction.DESC, "createtime");
            Page<OperationLog> operationLogs =operationLogDao.findByCreator(creator,PageRequest.of(pageIndex.intValue()-1,pageSize.intValue(),sort));
            Page<OperationLogDTO> operationLogList = operationLogs.map((item)->{
                OperationLogDTO operationLogDTO = new OperationLogDTO();
                BeanUtils.copyProperties(item,operationLogDTO);
                return operationLogDTO;
            });
            return operationLogList;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Page<OperationLogDTO> findOperationLogByType(LogType type, Integer pageIndex, Integer pageSize) {
        try{
            pageSize = pageSize > 100?100:pageSize;
            Sort sort = new Sort(Sort.Direction.DESC, "createtime");
            Page<OperationLog> operationLogs =operationLogDao.findByType(type.ordinal(),PageRequest.of(pageIndex.intValue()-1,pageSize.intValue(),sort));
            Page<OperationLogDTO> operationLogList = operationLogs.map((item)->{
                OperationLogDTO operationLogDTO = new OperationLogDTO();
                BeanUtils.copyProperties(item,operationLogDTO);
                return operationLogDTO;
            });
            return operationLogList;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Page<OperationLogDTO> findOperationLogByTypeAndCreator(LogType type,String creator, Integer pageIndex, Integer pageSize) {
        try{
            pageSize = pageSize > 100?100:pageSize;
            Sort sort = new Sort(Sort.Direction.DESC, "createtime");
            Page<OperationLog> operationLogs =operationLogDao.findOperationLogByTypeAndCreator(type.ordinal(),creator,PageRequest.of(pageIndex.intValue()-1,pageSize.intValue(),sort));
            Page<OperationLogDTO> operationLogList = operationLogs.map((item)->{
                OperationLogDTO operationLogDTO = new OperationLogDTO();
                BeanUtils.copyProperties(item,operationLogDTO);
                return operationLogDTO;
            });
            return operationLogList;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
