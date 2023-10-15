package com.wzy.zyapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.exception.BusinessException;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.mapper.InterfaceInfoMapper;
import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceQueryRequest;
import com.wzy.zyapi.model.entity.InterfaceInfo;
import com.wzy.zyapi.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 接口文档相关接口
 * @author: Wangzhaoyi
 * @create: 2023-09-18 20:23
 **/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Autowired
    InterfaceInfoMapper interfaceInfoMapper;


    @Override
    public void validPost(InterfaceInfo interfaceInfo,boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String method = interfaceInfo.getMethod();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, description,url,method), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称过长！");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "描述过长！");
        }
        if (StringUtils.isNotBlank(url) && url.length() > 512) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "URL地址过长");
        }
        if (StringUtils.isNotBlank(method) && method.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求类型过长");
        }
    }

    @Override
    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceQueryRequest interfaceQueryRequest) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        if(interfaceQueryRequest == null){
            return queryWrapper;
        }
        queryWrapper.like(StringUtils.isNotBlank(interfaceQueryRequest.getName()),"name",interfaceQueryRequest.getName());
        queryWrapper.like(StringUtils.isNotBlank(interfaceQueryRequest.getDescription()),"description",interfaceQueryRequest.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(interfaceQueryRequest.getUrl()),"url",interfaceQueryRequest.getUrl());
        queryWrapper.like(StringUtils.isNotBlank(interfaceQueryRequest.getRequestHeader()),"request_header",interfaceQueryRequest.getRequestHeader());
        queryWrapper.like(StringUtils.isNotBlank(interfaceQueryRequest.getResponseHeader()),"response_header",interfaceQueryRequest.getResponseHeader());
//        queryWrapper.like(interfaceQueryRequest.getStatus()!=null, "status",interfaceQueryRequest.getStatus());
        queryWrapper.like(StringUtils.isNotBlank(interfaceQueryRequest.getMethod()), "method", interfaceQueryRequest.getMethod());
        return queryWrapper;
    }

    @Override
    public List<InterfaceInfo> getlist() {
        List<InterfaceInfo> interfaceInfos = interfaceInfoMapper.selectList(null);
        System.out.println(interfaceInfos);
        return interfaceInfos;
    }
}
