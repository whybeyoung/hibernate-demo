/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: OperationLogDao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.OperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈操作日志接口〉
 *
 * @author xwliu
 * @create 2018/4/18
 */
public interface OperationLogDao extends JpaRepository<OperationLog,Integer> {

    /**
     * 分页按类型查询日志列表
     * @param type 日志类型
     * @param pageable 分页参数
     * @return
     */
    Page<OperationLog> findByType(Integer type,Pageable pageable);

    /**
     * 分页按创建者查询日志列表
     * @param creator 创建者
     * @param pageable 分页参数
     * @return
     */
    Page<OperationLog> findByCreator(String creator,Pageable pageable);

    /**
     * 分页按类型创建者查询日志列表
     * @param type 日志类型
     * @param creator 创建者
     * @param pageable 分页参数
     * @return
     */
    Page<OperationLog> findOperationLogByTypeAndCreator(Integer type,String creator,Pageable pageable);
}
