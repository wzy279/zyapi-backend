package com.wzy.zyapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.zyapi.model.dto.UserInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.wzy.zyapi.model.vo.UserInterfaceInfo.UserInterfaceInfoVo;
import com.wzy.zycommon.model.entity.UserInterfaceInfo;
    /**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-10-11 22:05
**/

public interface UserInterfaceInfoService extends IService<UserInterfaceInfo>{

    public boolean validPost(UserInterfaceInfo userInterfaceInfo,boolean add);


    public boolean add(long interfaceId,long userId);

    public QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest);

    public Page<UserInterfaceInfoVo> getList(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest);

}
