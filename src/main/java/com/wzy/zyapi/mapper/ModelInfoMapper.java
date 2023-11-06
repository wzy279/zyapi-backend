package com.wzy.zyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.zyapi.model.entity.ModelInfo;
import com.wzy.zyapi.model.vo.BigModel.BigModelListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-11-04 15:41
**/
@Mapper
public interface ModelInfoMapper extends BaseMapper<ModelInfo> {

    List<BigModelListVO> selectNameByUserId(@Param("userId")Long userId);

    String selectMessageByIdAndUserId(@Param("id")Long id,@Param("userId")Long userId);




}
