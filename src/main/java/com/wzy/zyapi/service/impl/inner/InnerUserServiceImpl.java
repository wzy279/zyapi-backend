package com.wzy.zyapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.service.UserService;
import com.wzy.zycommon.model.entity.User;
import com.wzy.zycommon.service.InnerUserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-15 16:11
 **/
//TODO 如果启用dubbo需要解开这个注解
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserService userService;
    @Override
    public User getInvokeUser(String accessKey) {
        ThrowUtils.throwIf(accessKey==null, ErrorCode.PARAMS_ERROR);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        User user = userService.getOne(queryWrapper);
        return user;
    }
}
