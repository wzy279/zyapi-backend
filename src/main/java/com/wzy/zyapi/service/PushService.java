package com.wzy.zyapi.service;


import com.wzy.zyapi.common.BaseResponse;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-04 10:50
 **/
public interface PushService {

    void pushToOne(String uid, String text);

    void pushToAll(String text);

    BaseResponse<String> pushMessageToXFServer(Long id,String uid, String text);
}
