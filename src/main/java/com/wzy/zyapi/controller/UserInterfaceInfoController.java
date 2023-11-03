package com.wzy.zyapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.zyapi.annotation.AuthCheck;
import com.wzy.zyapi.common.*;
import com.wzy.zyapi.constant.UserConstant;
import com.wzy.zyapi.exception.BusinessException;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.model.dto.UserInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.wzy.zyapi.model.dto.UserInterfaceInfo.UserInterfaceInfoUpdateRequest;
import com.wzy.zyapi.model.enums.UserInterfaceInfoStatusEnum;
import com.wzy.zyapi.model.vo.UserInterfaceInfo.UserInterfaceInfoVo;
import com.wzy.zyapi.service.UserInterfaceInfoService;
import com.wzy.zyapi.service.UserService;
import com.wzy.zycommon.model.entity.User;
import com.wzy.zycommon.model.entity.UserInterfaceInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户调用接口关系(user_interface_info)表控制层
 *
 * @author Wangzhaoyi
 */
@RestController
@RequestMapping("/user_interface_info")
public class UserInterfaceInfoController {
    /**
     * 服务对象
     */
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    private UserService userService;

    /**
     * 新增用户调用关系
     *
     * @param idRequest 接口表单数据
     * @param request             请求对象
     * @return 新增接口返回的接口id
     */
    @PostMapping("add")
    public BaseResponse<Void> addInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(idRequest==null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        boolean result = userInterfaceInfoService.add(idRequest.getId(), loginUser.getId());
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(null);
    }

    /**
     * 删除调用关系
     * @param deleteRequest 删除请求参数
     * @param request 请求对象
     * @return 删除接口的结果
     */
    @PostMapping("delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteInterface(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request){
        if (deleteRequest==null||deleteRequest.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(userInterfaceInfo==null,ErrorCode.NOT_FOUND_ERROR);
        boolean result = userInterfaceInfoService.removeById(userInterfaceInfo.getId());
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 更新接口信息
     * @param userInterfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterface(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest, HttpServletRequest request) {
        if (userInterfaceInfoUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoUpdateRequest, userInterfaceInfo);
        userInterfaceInfoService.validPost(userInterfaceInfo,false);
        long id =userInterfaceInfo.getId();
        //判断是否存在这个接口
        UserInterfaceInfo oldInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo==null,ErrorCode.NOT_FOUND_ERROR);
        boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 获取接口列表
     * @param userInterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @PostMapping("getlist")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserInterfaceInfoVo>> getInterfacelist(@RequestBody(required = false) UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest, HttpServletRequest request){
        long current = userInterfaceInfoQueryRequest.getCurrent();
        long size = userInterfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current,size));
        Page<UserInterfaceInfoVo> userInterfaceInfoVoPage = userInterfaceInfoService.getList(userInterfaceInfoQueryRequest);
        return ResultUtils.success(userInterfaceInfoVoPage);
    }

    /**
     * 根据id查询接口信息
     * @param id
     * @param request
     * @return
     */
    @GetMapping("getById")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<UserInterfaceInfo> getById(long id,HttpServletRequest request){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(userInterfaceInfo==null,ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(userInterfaceInfo);
    }


    @GetMapping("allowCall")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> allowCall(long id, HttpServletRequest request){
        ThrowUtils.throwIf(id<0, ErrorCode.PARAMS_ERROR);
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        User loginUser = userService.getLoginUser(request);
        queryWrapper.eq("user_id", loginUser.getId());
        queryWrapper.eq("interfaceInfo_id", id);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getOne(queryWrapper);
        ThrowUtils.throwIf(userInterfaceInfo==null, ErrorCode.NOT_FOUND_ERROR);
        if(userInterfaceInfo.getStatus().equals(UserInterfaceInfoStatusEnum.ALLOW.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }else {
            userInterfaceInfo.setStatus(UserInterfaceInfoStatusEnum.ALLOW.getValue());
            return ResultUtils.success(userInterfaceInfoService.updateById(userInterfaceInfo));
        }
    }

    @GetMapping("banCall")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> banCall(long id, HttpServletRequest request){
        ThrowUtils.throwIf(id<0, ErrorCode.PARAMS_ERROR);
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        User loginUser = userService.getLoginUser(request);
        queryWrapper.eq("user_id", loginUser.getId());
        queryWrapper.eq("interfaceInfo_id", id);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getOne(queryWrapper);
        ThrowUtils.throwIf(userInterfaceInfo==null, ErrorCode.NOT_FOUND_ERROR);
        if(userInterfaceInfo.getStatus().equals(UserInterfaceInfoStatusEnum.CANCLE.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }else {
            userInterfaceInfo.setStatus(UserInterfaceInfoStatusEnum.CANCLE.getValue());
            return ResultUtils.success(userInterfaceInfoService.updateById(userInterfaceInfo));
        }
    }



}
