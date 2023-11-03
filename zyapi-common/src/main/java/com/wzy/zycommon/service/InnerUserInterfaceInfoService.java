package com.wzy.zycommon.service;


/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-10-11 22:05
**/

public interface InnerUserInterfaceInfoService {

    public boolean validCount(long interfaceId,long userId);

    public boolean queryInterfaceStatus(long interfaceId, long userId);

}
