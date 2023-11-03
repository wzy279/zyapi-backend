package com.wzy.zyapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.service.UserInterfaceInfoService;
import com.wzy.zycommon.model.entity.UserInterfaceInfo;
import com.wzy.zycommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-15 16:06
 **/
//TODO 如果启用dubbo需要解开这个注解
@DubboService
public class InnerUserInterfaceServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean validCount(long interfaceId, long userId) {
        ThrowUtils.throwIf(interfaceId<0||userId<0, ErrorCode.PARAMS_ERROR);
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfo_id",interfaceId);
        updateWrapper.eq("user_id",userId);
        updateWrapper.setSql("left_num = left_num - 1,total_num = total_num + 1");
        boolean result = userInterfaceInfoService.update(updateWrapper);
        return result;
    }

    @Override
    public boolean queryInterfaceStatus(long interfaceId, long userId) {

        return false;
    }
}
