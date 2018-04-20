/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ImageService
 * Author:   xwliu
 * Date:     2018/4/2 15:09
 * Description: 镜像服务
 */
package com.iflytek.iaas.service;


import com.iflytek.iaas.dto.ImageDTO;
import com.iflytek.iaas.vo.ImageVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 〈镜像服务接口〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
public interface ImageService {


    /**
     * 查询镜像
     *
     * @param name
     * @param version
     * @param page
     * @param pagesize
     * @return
     */
    public Page<ImageDTO> findByNameLike(String name, String version, Integer page, Integer pagesize);


    /**
     * 保存镜像
     *
     * @param imageVO
     * @return
     */
    public Boolean saveImage(ImageVO imageVO);

    /**
     * 删除镜像
     * @param imageId
     * @return
     */
    public Boolean deleteImage(Integer imageId);


    /**
     * 查询image选择
     * @param name
     * @return
     */
    public List<ImageDTO> searchImages(String name);
}