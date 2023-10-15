package com.wzy.zyapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.zyapi.annotation.AuthCheck;
import com.wzy.zyapi.common.BaseResponse;
import com.wzy.zyapi.common.DeleteRequest;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.common.ResultUtils;
import com.wzy.zyapi.constant.UserConstant;
import com.wzy.zyapi.exception.BusinessException;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.model.dto.UserInterfaceInfo.UserInterfaceInfoAddRequest;
import com.wzy.zyapi.model.dto.UserInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.wzy.zyapi.model.dto.UserInterfaceInfo.UserInterfaceInfoUpdateRequest;
import com.wzy.zyapi.model.entity.User;
import com.wzy.zyapi.model.entity.UserInterfaceInfo;
import com.wzy.zyapi.service.UserInterfaceInfoService;
import com.wzy.zyapi.service.UserService;
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
     * @param userInterfaceInfoAddRequest 接口表单数据
     * @param request             请求对象
     * @return 新增接口返回的接口id
     */
    @PostMapping("add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest, HttpServletRequest request) {
        if (userInterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.getLoginUser(request);
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
        userInterfaceInfoService.validPost(userInterfaceInfo,true);
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long id = userInterfaceInfo.getId();
        return ResultUtils.success(id);
    }

    /**
     * 删除接口
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
    public BaseResponse<Page<UserInterfaceInfo>> getInterfacelist(@RequestBody(required = false) UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest, HttpServletRequest request){
        long current = userInterfaceInfoQueryRequest.getCurrent();
        long size = userInterfaceInfoQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<InterfaceInfo> interfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size), userInterfaceInfoService.getQueryWrapper(interfaceQueryRequest));
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size), null);
        System.out.println(userInterfaceInfoPage.getRecords());
        return ResultUtils.success(userInterfaceInfoPage);
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


}
