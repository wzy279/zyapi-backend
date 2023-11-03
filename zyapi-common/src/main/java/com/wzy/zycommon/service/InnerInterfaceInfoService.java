package com.wzy.zycommon.service;

import com.wzy.zycommon.model.entity.InterfaceInfo;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-15 15:36
 **/
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     */
    InterfaceInfo getInterfaceInfo(String path, String method);

    String sayHello();
}

