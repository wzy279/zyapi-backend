package com.wzy.zyapi.controller;

import com.wzy.zyapi.common.BaseResponse;
import com.wzy.zyapi.common.ResultUtils;
import com.wzy.zyapi.model.entity.ModelInfo;
import com.wzy.zyapi.model.vo.BigModel.BigModelListVO;
import com.wzy.zyapi.model.vo.BigModel.ModelMessageVO;
import com.wzy.zyapi.service.ModelInfoService;
import com.wzy.zyapi.service.PushService;
import com.wzy.zyapi.service.UserService;
import com.wzy.zycommon.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-04 10:50
 **/
@Slf4j
@RestController
@RequestMapping("/xfModel")
public class XFMessageController {

    @Autowired
    private PushService pushService;

    @Resource
    private UserService userService;

    @Resource
    private ModelInfoService modelInfoService;

    @GetMapping("/test")
    public BaseResponse<String> test(Long id,String text, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return pushService.pushMessageToXFServer(id,String.valueOf(loginUser.getId()), text);
    }


    @GetMapping("list")
    public BaseResponse<List<BigModelListVO>> getlist(HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        List<BigModelListVO> list = modelInfoService.queryList(loginUser.getId());
        return ResultUtils.success(list);
    }

    @GetMapping("localmessage")
    public BaseResponse<List<ModelMessageVO>> getlocalmessage(long id, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        List<ModelMessageVO> list = modelInfoService.queryLocalMessage(id,loginUser.getId());
        return ResultUtils.success(list);
    }
    @GetMapping("create")
    public BaseResponse<Long> createRecord(String name,HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setName(name);
        modelInfo.setUserId(loginUser.getId());
        modelInfoService.save(modelInfo);
        return ResultUtils.success(modelInfo.getId());
    }
}
