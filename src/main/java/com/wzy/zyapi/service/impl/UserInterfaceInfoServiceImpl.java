package com.wzy.zyapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.exception.BusinessException;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.mapper.UserInterfaceInfoMapper;
import com.wzy.zyapi.model.entity.UserInterfaceInfo;
import com.wzy.zyapi.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;
/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-10-11 22:05
**/

@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements UserInterfaceInfoService{

    @Override
    public boolean validPost(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if(userInterfaceInfo==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(add){
            if(userInterfaceInfo.getUserId()<=0||userInterfaceInfo.getInterfaceinfoId()<=0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户异常！");
            }
        }
        if(userInterfaceInfo.getLeftNum()<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"剩余次数不能小于0");
        }
        return true;
    }

    @Override
    public boolean validCount(long interfaceid, long userid) {
        ThrowUtils.throwIf(interfaceid<0||userid<0,ErrorCode.PARAMS_ERROR);
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfo_id",interfaceid);
        updateWrapper.eq("user_id",userid);
        updateWrapper.setSql("left_num = left_num - 1,total_num = total_num + 1");
        boolean result = this.update(updateWrapper);
        return result;
    }

}
