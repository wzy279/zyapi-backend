package com.wzy.zyapi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description 接口信息的请求方法
 * @author: Wangzhaoyi
 * @create: 2023-09-19 14:46
 **/
@Data
public class InterfaceAddRequest implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;
}
