package com.wzy.zyapi.model.vo.UserInterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-17 15:08
 **/
@Data
public class UserInterfaceInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 调用用户 id
     */
    private Long userId;
    /**
     * 调用用户名称
     */
    private String userName;

    /**
     * 接口 id
     */
    private Long interfaceinfoId;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 0-正常，1-禁用
     */
    private Integer status;
}
