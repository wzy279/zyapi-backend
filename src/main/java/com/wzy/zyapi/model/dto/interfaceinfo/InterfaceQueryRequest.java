package com.wzy.zyapi.model.dto.interfaceinfo;

import com.wzy.zyapi.common.PageRequest;
import lombok.Data;

import java.util.Date;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-09-23 14:12
 **/
@Data
public class InterfaceQueryRequest extends PageRequest {

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

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;


}
