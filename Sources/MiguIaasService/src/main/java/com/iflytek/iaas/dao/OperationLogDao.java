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

    Page<OperationLog> findALL(Pageable pageable);

    Page<OperationLog> findByType(Integer type,Pageable pageable);

    Page<OperationLog> findByCreator(String creator,Pageable pageable);
}
