package com.wzy.zyapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceQueryRequest;
import com.wzy.zycommon.model.entity.InterfaceInfo;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-09-18 20:22
 **/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    public void validPost(InterfaceInfo interfaceInfo,boolean add);


    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceQueryRequest interfaceQueryRequest);

}
