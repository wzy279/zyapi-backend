package com.wzy.zyapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.exception.BusinessException;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.mapper.InterfaceInfoMapper;
import com.wzy.zyapi.mapper.UserInterfaceInfoMapper;
import com.wzy.zyapi.mapper.UserMapper;
import com.wzy.zyapi.model.dto.UserInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.wzy.zyapi.model.vo.UserInterfaceInfo.UserInterfaceInfoVo;
import com.wzy.zyapi.service.UserInterfaceInfoService;
import com.wzy.zycommon.model.entity.InterfaceInfo;
import com.wzy.zycommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-10-11 22:05
**/

@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements UserInterfaceInfoService{

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public boolean validPost(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if(userInterfaceInfo==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(add){
            if(userInterfaceInfo.getUserId()<=0||userInterfaceInfo.getInterfaceinfoId()<=0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口id或用户id异常！");
            }
        }
        ThrowUtils.throwIf(userInterfaceInfo.getLeftNum()<0, ErrorCode.PARAMS_ERROR,"剩余次数不能小于0");

        return true;
    }

    @Override
    public boolean add(long interfaceId, long userId) {
//        1. 判断接口id是否合理
        ThrowUtils.throwIf(interfaceId<0,ErrorCode.PARAMS_ERROR);
//        2. 获取这个接口的详细信息
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(interfaceId);
        ThrowUtils.throwIf(interfaceInfo==null,ErrorCode.NOT_FOUND_ERROR,"接口不存在");
//        3. 看看这个接口是不是开着的,
        ThrowUtils.throwIf(interfaceInfo.getStatus()==0,ErrorCode.OPERATION_ERROR,"接口已关闭!");
//       4. 查看这个接口是否有与用户的调用关系
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("interfaceInfo_id",interfaceId);
        UserInterfaceInfo userInterfaceInfo = this.getOne(queryWrapper);
        //如果这个接口已经有了
        ThrowUtils.throwIf(userInterfaceInfo != null, ErrorCode.OPERATION_ERROR, "不能重复申请!");
//        5. 插入数据,设置剩余调用次数为10,总调用次数为0
        UserInterfaceInfo newUserInterfaceInfo = new UserInterfaceInfo();
        newUserInterfaceInfo.setInterfaceinfoId(interfaceId);
        newUserInterfaceInfo.setUserId(userId);
        newUserInterfaceInfo.setLeftNum(10);
        boolean save = this.save(newUserInterfaceInfo);
        return save;
    }

    @Override
    public QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest) {
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userInterfaceInfoQueryRequest.getUserId()!=0, "user_id", userInterfaceInfoQueryRequest.getUserId());
        queryWrapper.eq(userInterfaceInfoQueryRequest.getInterfaceinfoId()!=0, "interfaceInfo_id", userInterfaceInfoQueryRequest.getInterfaceinfoId());
        queryWrapper.eq(userInterfaceInfoQueryRequest.getStatus()==1, "status", userInterfaceInfoQueryRequest.getStatus());
        return queryWrapper;
    }

    @Override
    public Page<UserInterfaceInfoVo> getList(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest) {
        long current = userInterfaceInfoQueryRequest.getCurrent();
        long size = userInterfaceInfoQueryRequest.getPageSize();
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        System.out.println(userInterfaceInfoQueryRequest.getUserId());
        queryWrapper.eq(userInterfaceInfoQueryRequest.getUserId()!=null, "user_id", userInterfaceInfoQueryRequest.getUserId());
        queryWrapper.eq(userInterfaceInfoQueryRequest.getInterfaceinfoId()!=null, "interfaceInfo_id", userInterfaceInfoQueryRequest.getInterfaceinfoId());
        //TODO 无法获取到数据
//        Page<UserInterfaceInfo> page = this.page(new Page<>(current, size), queryWrapper);
////        Page<UserInterfaceInfo> page = this.page(new Page<>(current, size));
//        Page<UserInterfaceInfoVo> result = new Page<>();
//        result.setSize(page.getSize());
//        result.setCurrent(page.getCurrent());
//        result.setTotal(page.getTotal());
//        List<UserInterfaceInfoVo> list = page.getRecords().stream().map(item -> {
//            UserInterfaceInfoVo userInterfaceInfoVo = new UserInterfaceInfoVo();
//            BeanUtils.copyProperties(item, userInterfaceInfoVo);
////            userInterfaceInfoVo.setUserAccount(userMapper.selectUserAccountById(userInterfaceInfoVo.getUserId()));
////            userInterfaceInfoVo.setInterfaceName(interfaceInfoMapper.selectNameById(userInterfaceInfoVo.getInterfaceinfoId()));
//            return userInterfaceInfoVo;
//        }).collect(Collectors.toList());
        Page<UserInterfaceInfoVo> result = new Page<>();
        result.setTotal(this.count(queryWrapper));
        result.setSize(size);
        long maxnpage = result.getTotal()/size;
        if(result.getTotal() % size != 0){
            maxnpage++;
        }
        if(current> maxnpage){
            current= maxnpage;
        }
        //算出来从是第几页了
        userInterfaceInfoQueryRequest.setCurrent((current-1)*size);
        List<UserInterfaceInfoVo> list = userInterfaceInfoMapper.getListt(userInterfaceInfoQueryRequest);
        result.setRecords(list);
        return result;
    }


}
