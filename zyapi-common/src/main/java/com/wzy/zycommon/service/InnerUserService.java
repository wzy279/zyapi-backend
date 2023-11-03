package com.wzy.zycommon.service;

import com.wzy.zycommon.model.entity.User;

public interface InnerUserService {

    /**
     * 数据库中查是否已分配给用户秘钥（accessKey）
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
