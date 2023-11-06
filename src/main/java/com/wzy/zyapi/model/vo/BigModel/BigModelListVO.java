package com.wzy.zyapi.model.vo.BigModel;

import lombok.Data;

import java.io.Serializable;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-05 08:43
 **/
@Data
public class BigModelListVO implements Serializable {

    /**
     * 记录id
     */
    private Long id;
    /**
     * 记录名
     */
    private String name;
}
