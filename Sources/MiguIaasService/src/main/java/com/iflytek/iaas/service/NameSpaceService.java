/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: NameSpaceService
 */
package com.iflytek.iaas.service;

import com.iflytek.iaas.dto.NameSpaceDTO;
import com.iflytek.iaas.vo.NameSpaceVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 〈业务日志服务〉
 *
 * @author ruizhao3
 * @create 2018/4/20
 */
public interface NameSpaceService {


    /**
     * 分页查询命名空间列表
     * @param ns 关键字
     * @param page　页码
     * @param pagesize　页长
     * @return
     */
    Page<NameSpaceDTO> findPagedNs(String ns, Integer page, Integer pagesize);

    /**
     * 搜索命名空间
     * @param ns
     * @return
     */
    List<String>  searchNsList(String ns);

    /**
     * 保存命名空间
     * @param nsVO
     * @return
     */
    Integer saveNs(NameSpaceVO nsVO);

    /**
     * 更新命名空间
     * @param id
     * @param annotation
     * @return
     */
    boolean updateNs(Integer id, String annotation);

    /**
     * 删除命名空间
     * @param id
     * @return
     */
    boolean removeNs(Integer id);
}
