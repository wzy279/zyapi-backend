package com.wzy.zyapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.wzy.zyapi.annotation.RequestLimit;
import com.wzy.zyapi.common.*;
import com.wzy.zyapi.exception.BusinessException;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceAddRequest;
import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceInvokeRequest;
import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceQueryRequest;
import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceUpdateRequest;
import com.wzy.zyapi.model.enums.InterfaceInfoStatusEnum;
import com.wzy.zyapi.model.enums.UserInterfaceInfoStatusEnum;
import com.wzy.zyapi.service.InterfaceInfoService;
import com.wzy.zyapi.service.UserInterfaceInfoService;
import com.wzy.zyapi.service.UserService;
import com.wzy.zycommon.model.entity.InterfaceInfo;
import com.wzy.zycommon.model.entity.User;
import com.wzy.zycommon.model.entity.UserInterfaceInfo;
import com.zyapi.zyapisdk.client.ZyapiClient;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-09-18 20:24
 **/
@RestController
@RequestMapping("/interfaceinfo")
@Slf4j
@ApiModel("接口相关接口实现类")
public class InterfaceInfoController {
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    private UserService userService;
    @Resource
    private ZyapiClient zyapiClient;


    /**
     * 新增接口
     *
     * @param interfaceAddRequest 接口表单数据
     * @param request             请求对象
     * @return 新增接口返回的接口id
     */
    @PostMapping("add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceAddRequest interfaceAddRequest, HttpServletRequest request) {
        if (interfaceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceAddRequest, interfaceInfo);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setCreateBy(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long id = interfaceInfo.getId();
        return ResultUtils.success(id);
    }

    /**
     * 删除接口
     * @param deleteRequest 删除请求参数
     * @param request 请求对象
     * @return 删除接口的结果
     */
    @PostMapping("delete")
    public BaseResponse<Boolean> deleteInterface(@RequestBody DeleteRequest deleteRequest,HttpServletRequest request){
        if (deleteRequest==null||deleteRequest.getId()<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(deleteRequest.getId());
        ThrowUtils.throwIf(interfaceInfo==null,ErrorCode.NOT_FOUND_ERROR);
        if(!loginUser.getId().equals(interfaceInfo.getCreateBy()) && !userService.isAdmin(loginUser)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.removeById(interfaceInfo.getId());
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 更新接口信息
     * @param interfaceUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("update")
    public BaseResponse<Boolean> updateInterface(@RequestBody InterfaceUpdateRequest interfaceUpdateRequest, HttpServletRequest request) {
        if (interfaceUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceUpdateRequest, interfaceInfo);
        interfaceInfoService.validPost(interfaceInfo,false);
        long id =interfaceInfo.getId();
        //判断是否存在这个接口
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo==null,ErrorCode.NOT_FOUND_ERROR);
        interfaceInfo.setUpdateBy(loginUser.getId());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 获取接口列表
     * @param interfaceQueryRequest
     * @param request
     * @return
     */
    @PostMapping("getlist")
    public BaseResponse<Page<InterfaceInfo>> getInterfacelist(@RequestBody(required = false) InterfaceQueryRequest interfaceQueryRequest,HttpServletRequest request){
        long current = interfaceQueryRequest.getCurrent();
        long size = interfaceQueryRequest.getPageSize();
        log.info("current is {}",current);
        log.info("size is {}",size);
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), interfaceInfoService.getQueryWrapper(interfaceQueryRequest));
//        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), null);
        System.out.println(interfaceInfoPage.getRecords());
        log.info("the interfaceInfoPage is :{}", interfaceInfoPage);

        return ResultUtils.success(interfaceInfoPage);
    }

    /**
     * 根据id查询接口信息
     * @param id
     * @param request
     * @return
     */
    @GetMapping("getById")
    public BaseResponse<InterfaceInfo> getById(long id,HttpServletRequest request){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if(interfaceInfo==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(interfaceInfo);
    }


    /**
     * 上线接口
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("online")
    public BaseResponse<Boolean> onlineInterface(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = idRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if(interfaceInfo==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        com.zyapi.zyapisdk.model.User user = new com.zyapi.zyapisdk.model.User();
        user.setName("1111");
        String name = zyapiClient.getnamepost2(user);
        if (StringUtils.isBlank(name)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口验证错误");
        }
        InterfaceInfo interfaceInfo1 = new InterfaceInfo();
        interfaceInfo1.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        interfaceInfo1.setId(id);
        boolean result = interfaceInfoService.updateById(interfaceInfo1);
        return ResultUtils.success(result);
    }

    /**
     * 下线接口
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("offline")
    public BaseResponse<Boolean> offlineInterface(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = idRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if(interfaceInfo==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        com.zyapi.zyapisdk.model.User user = new com.zyapi.zyapisdk.model.User();
        user.setName("1111");
        String name = zyapiClient.getnamepost2(user);
        if (StringUtils.isBlank(name)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口验证错误");
        }
        InterfaceInfo interfaceInfo1 = new InterfaceInfo();
        interfaceInfo1.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        interfaceInfo1.setId(id);
        boolean result = interfaceInfoService.updateById(interfaceInfo1);
        return ResultUtils.success(result);
    }


    /**
     * 用户请求接口
     * @param invokeRequest 接口请求参数
     * @param request
     * @return
     */
    @RequestLimit(count = 3)
    @PostMapping("invoke")
    public BaseResponse<Object> invokeInterface(@RequestBody InterfaceInvokeRequest invokeRequest, HttpServletRequest request) {
        //看看参数对不对
        ThrowUtils.throwIf(invokeRequest == null||invokeRequest.getId()<0,ErrorCode.PARAMS_ERROR);
        //看看接口是不是存在并且开启的
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(invokeRequest.getId());
        ThrowUtils.throwIf(interfaceInfo == null || !interfaceInfo.getStatus().equals(InterfaceInfoStatusEnum.ONLINE.getValue()), ErrorCode.PARAMS_ERROR);
        //获取到用户信息
        User loginUser = userService.getLoginUser(request);
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interfaceInfo_id",invokeRequest.getId());
        queryWrapper.eq("user_id",loginUser.getId());
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getOne(queryWrapper);
        ThrowUtils.throwIf(userInterfaceInfo == null||userInterfaceInfo.getLeftNum()<=0|| userInterfaceInfo.getStatus().equals(UserInterfaceInfoStatusEnum.CANCLE.getValue()), ErrorCode.NO_AUTH_ERROR);
        //获取到accesskey和secretkey
        String accessKey = loginUser.getAccesskey();
        String secretKey = loginUser.getSecretkey();
        Gson gson = new Gson();
        com.zyapi.zyapisdk.model.User user = gson.fromJson(invokeRequest.getRequestBody(),com.zyapi.zyapisdk.model.User.class);
        Map param = gson.fromJson(invokeRequest.getRequestBody(), Map.class);
        ZyapiClient tempzyapiClient = new ZyapiClient(accessKey,secretKey);
        //调用这个接口
        String result = "111";
        if(interfaceInfo.getMethod().equals("GET")){
            //如果是get请求
            result = tempzyapiClient.GETgenericRequest(interfaceInfo.getUrl(),param);
        }else {
            result = tempzyapiClient.POSTgenericRequest(interfaceInfo.getUrl(),param);
        }
        //获得返回值，返回给前端
        return ResultUtils.success(result);
    }



}
