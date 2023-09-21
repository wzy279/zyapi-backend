package com.wzy.zyapi.controller;

import com.wzy.zyapi.common.BaseResponse;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.exception.BusinessException;
import com.wzy.zyapi.model.dto.interfaceinfo.InterfaceAddRequest;
import com.wzy.zyapi.model.entity.InterfaceInfo;
import com.wzy.zyapi.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-09-18 20:24
 **/
@RestController
@RequestMapping("/interfaceinfo")
@Slf4j
public class InterfaceInfoController {
    @Resource
    private InterfaceInfoService interfaceInfoService;



    @PostMapping("add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceAddRequest interfaceAddRequest, HttpServletRequest request){
        if(interfaceAddRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceAddRequest, interfaceInfo);
        //看看数据库中有没有一样的，如果有一样的就报错
        boolean result = interfaceInfoService.save(interfaceInfo);
        return null;
    }
}
