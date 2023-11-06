package com.wzy.zyapi.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.mapper.ModelInfoMapper;
import com.wzy.zyapi.model.entity.ModelInfo;
import com.wzy.zyapi.model.vo.BigModel.BigModelListVO;
import com.wzy.zyapi.model.vo.BigModel.ModelMessageVO;
import com.wzy.zyapi.service.ModelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-11-04 15:41
**/

@Service
public class ModelInfoServiceImpl extends ServiceImpl<ModelInfoMapper, ModelInfo> implements ModelInfoService{

    @Autowired
    private ModelInfoMapper modelInfoMapper;
    @Override
    public List<BigModelListVO> queryList(Long userId) {
        List<BigModelListVO> list = modelInfoMapper.selectNameByUserId(userId);
        return list;
    }

    @Override
    public List<ModelMessageVO> queryLocalMessage(Long id, Long userId) {
        ThrowUtils.throwIf(id==null|| userId == null, ErrorCode.NOT_FOUND_ERROR);
        String list = modelInfoMapper.selectMessageByIdAndUserId(id, userId);
        JSONArray jsonArray = JSONUtil.parseArray(list);
        List<ModelMessageVO> modelMessageVOList = JSONUtil.toList(jsonArray, ModelMessageVO.class);
        return modelMessageVOList;
    }
}
