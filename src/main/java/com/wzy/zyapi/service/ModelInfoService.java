package com.wzy.zyapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzy.zyapi.model.entity.ModelInfo;
import com.wzy.zyapi.model.vo.BigModel.BigModelListVO;
import com.wzy.zyapi.model.vo.BigModel.ModelMessageVO;

import java.util.List;

/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-11-04 15:41
**/

public interface ModelInfoService extends IService<ModelInfo>{



    List<BigModelListVO> queryList(Long userId);


    List<ModelMessageVO> queryLocalMessage(Long id, Long userId);


}
