package com.wzy.zyapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.service.InterfaceInfoService;
import com.wzy.zycommon.model.entity.InterfaceInfo;
import com.wzy.zycommon.service.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-15 16:14
 **/
//TODO 如果启用dubbo需要解开这个注解
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    private static final String GATEWAYHOST = "http://localhost:8090";

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        ThrowUtils.throwIf(path == null || method == null, ErrorCode.PARAMS_ERROR);
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", GATEWAYHOST+path);
        queryWrapper.eq("method", method);
        InterfaceInfo interfaceInfo = interfaceInfoService.getOne(queryWrapper);

        return interfaceInfo;
    }

    @Override
    public String sayHello() {
        return "网关启动成功";
    }
}
