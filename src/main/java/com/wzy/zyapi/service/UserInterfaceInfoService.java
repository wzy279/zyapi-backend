package com.wzy.zyapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.zyapi.model.entity.UserInterfaceInfo;
    /**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-10-11 22:05
**/

public interface UserInterfaceInfoService extends IService<UserInterfaceInfo>{

    public boolean validPost(UserInterfaceInfo userInterfaceInfo,boolean add);
    public boolean validCount(long interfaceid,long userid);

}
