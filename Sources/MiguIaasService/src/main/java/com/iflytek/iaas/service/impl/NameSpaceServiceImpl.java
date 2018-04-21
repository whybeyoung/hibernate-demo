/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: NameSpaceServiceImpl
 */
package com.iflytek.iaas.service.impl;

import com.iflytek.iaas.consts.ReturnCode;
import com.iflytek.iaas.dao.DeployNamespaceDao;
import com.iflytek.iaas.domain.DeployNamespace;
import com.iflytek.iaas.dto.NameSpaceDTO;
import com.iflytek.iaas.exception.BusiException;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.service.NameSpaceService;
import com.iflytek.iaas.vo.NameSpaceVO;
import io.kubernetes.client.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 〈命名空间服务〉
 *
 * @author ruizhao3
 * @create 2018/4/20
 */
@Service("NameSpaceService")
public class NameSpaceServiceImpl implements NameSpaceService {

    private Logger logger = LoggerFactory.getLogger(NameSpaceService.class);

    @Autowired
    private DeployNamespaceDao namespaceDao;

    @Autowired
    private K8SService k8SService;

    @Override
    public Page<NameSpaceDTO> findPagedNs(String ns, Integer page, Integer pagesize) {
        DeployNamespace nsEx = new DeployNamespace();
        if (!StringUtils.isEmpty(ns)) {
            nsEx.setNs("%" + ns + "%");
        }
        nsEx.setValid(true);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("ns", ExampleMatcher.GenericPropertyMatchers.contains());

        //创建实例
        Example<DeployNamespace> ex = Example.of(nsEx, matcher);
        Page<DeployNamespace> nss = namespaceDao.findAll(ex, PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "createtime")));

        Page<NameSpaceDTO> dtos = nss.map((model) -> {
            NameSpaceDTO dto = new NameSpaceDTO();
            BeanUtils.copyProperties(model, dto);
            return dto;
        });
        return dtos;
    }

    @Override
    public List<String> searchNsList(String ns) {
        DeployNamespace nsEx = new DeployNamespace();
        if (!StringUtils.isEmpty(ns)) {
            nsEx.setNs("%" + ns + "%");
        }
        nsEx.setValid(true);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("ns", ExampleMatcher.GenericPropertyMatchers.contains());

        //创建实例
        Example<DeployNamespace> ex = Example.of(nsEx, matcher);
        List<DeployNamespace> nss = namespaceDao.findAll(ex, new Sort(Sort.Direction.DESC, "createtime"));

        return nss.stream().map(p -> p.getNs()).collect(Collectors.toList());
    }

    @Override
    public Integer saveNs(NameSpaceVO nsVO) {
        if(namespaceDao.countByNsAndValid(nsVO.getNs(), true) > 0){
            throw new BusiException(ReturnCode.NS_EXIST);
        }
        try {
            k8SService.createDeployNamespace(nsVO.getNs());
            DeployNamespace ns = new DeployNamespace();
            BeanUtils.copyProperties(nsVO, ns);
            ns.setValid(true);
            return namespaceDao.save(ns).getId();
        } catch (Exception e) {
            logger.error("create NS failed, info=[ns={}] ", nsVO.getNs(), e);
            throw new BusiException(ReturnCode.EXCEPTION);
        }

    }

    @Override
    public boolean updateNs(Integer id, String annotation) {
        Optional<DeployNamespace> optional = namespaceDao.findById(id);
        if(!optional.isPresent()){
            throw new BusiException(ReturnCode.NS_NOTEXIST);
        }
        DeployNamespace ns = optional.get();
        ns.setAnnotation(annotation);
        return namespaceDao.saveAndFlush(ns) != null;
    }

    @Override
    public boolean removeNs(Integer id) {
        Optional<DeployNamespace> optional = namespaceDao.findById(id);
        if(!optional.isPresent()){
            return true;
        }
        DeployNamespace ns = optional.get();
        try {
            k8SService.deleteNamespace(ns.getNs());
            ns.setValid(false);
            return namespaceDao.saveAndFlush(ns) != null;
        } catch (Exception e) {
            logger.error("delete NS Failed, info=[id={}, ns={}]", id, ns.getNs(), e);
            throw new BusiException(ReturnCode.EXCEPTION);
        }

    }
}
