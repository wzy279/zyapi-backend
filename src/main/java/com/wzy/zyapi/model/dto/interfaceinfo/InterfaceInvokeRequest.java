package com.wzy.zyapi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-09-21 18:04
 **/
@Data
public class InterfaceInvokeRequest implements Serializable {

    private Long id;

    /**
     * 请求参数
     */
    private String requestBody;

    private static final long serialVersionUID = 1L;
}
