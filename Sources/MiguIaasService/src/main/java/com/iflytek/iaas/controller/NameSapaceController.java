/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: NameSapaceController
 * Author:   ruizhao3
 * Date:     2018/4/2 15:02
 * Description: 命名空间相关接口
 */
package com.iflytek.iaas.controller;

import com.alibaba.fastjson.JSON;
import com.iflytek.iaas.consts.ReturnCode;
import com.iflytek.iaas.exception.BusiException;
import com.iflytek.iaas.exception.ControllerException;
import com.iflytek.iaas.service.NameSpaceService;
import com.iflytek.iaas.utils.RegularUtils;
import com.iflytek.iaas.vo.NameSpaceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 〈命名空间相关接口〉
 *
 * @author ruizhao3
 * @create 2018/4/20
 */
@Api(value = "NameSpace-API", description = "命名空间相关接口")
@RequestMapping(path = "/api/v1")
@RestController
public class NameSapaceController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(NameSapaceController.class);

    @Autowired
    private NameSpaceService nameSpaceService;

    @ApiOperation(value = "createNameSpace", notes = "创建命名空间")
    @ApiImplicitParam(name = "ns", value = "命名空间", required = true, dataType = "ImageVO")
    @PostMapping("/nss")
    public String createNs(HttpServletRequest request, @RequestBody NameSpaceVO ns) throws ControllerException {

        if (StringUtils.isEmpty(ns.getNs())) {
            throw new ControllerException(ReturnCode.IMAGE_NULL_NAME);
        }
        if (!RegularUtils.isK8sAllowedName(ns.getNs())) {
            throw new ControllerException(ReturnCode.PARAM_ILLEGALNAME);
        }
        ns.setCreator(getCurrentUser(request).getNickname());
        try {
            nameSpaceService.saveNs(ns);
            return SUCCESS;
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            logger.info("saveNs error:", e);
            throw new ControllerException(ReturnCode.EXCEPTION);
        }
    }

    @ApiOperation(value = "deleteNameSpace", notes = "删除命名空间")
    @ApiImplicitParam(name = "id", value = "命名空间id", paramType = "path", required = true, dataType = "Integer")
    @DeleteMapping("/nss/{id}")
    public String deleteNs(HttpServletRequest request, @PathVariable("id") Integer id) throws ControllerException {
        try {
            nameSpaceService.removeNs(id);
        } catch (Exception e) {
            throw new ControllerException(ReturnCode.EXCEPTION);
        }
        return SUCCESS;
    }

    @ApiOperation(value = "queryNameSpace", notes = "查询命名空间")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "ns", value = "命名空间", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "页长", paramType = "query", required = false, dataType = "Integer"),
    })
    @GetMapping("/nss")
    public String queryImages(HttpServletRequest request,
                              @RequestParam(required = false) String ns,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer pagesize) {
        page = page == null || page <= 0 ? 1 : page;
        pagesize = pagesize == null || pagesize <= 0 ? 10 : pagesize;
        return JSON.toJSONString(nameSpaceService.findPagedNs(ns, page, pagesize));

    }

    @ApiOperation(value = "updateNameSpace", notes = "修改命名空间")
    @ApiImplicitParam(name = "annotation", value = "备注描述", paramType = "query", required = false, dataType = "String")
    @PutMapping("/nss/{id}")
    public String updateNs(HttpServletRequest request, @PathVariable("id") Integer id, @RequestParam(required = false, value = "annotation") String annotation) {
        nameSpaceService.updateNs(id, annotation);
        return SUCCESS;
    }

    @ApiOperation(value = "searchNameSpace", notes = "搜索命名空间")
    @ApiImplicitParam(name = "ns", value = "key", paramType = "query", required = false, dataType = "String")
    @GetMapping("/nss/search")
    public List<String> searchNs(HttpServletRequest request, @RequestParam(required = false, value = "ns") String ns) {
        return nameSpaceService.searchNsList(ns);
    }

}
