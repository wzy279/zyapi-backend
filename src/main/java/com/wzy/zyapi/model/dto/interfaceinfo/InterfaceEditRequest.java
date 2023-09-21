package com.wzy.zyapi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-09-19 16:03
 **/
@Data
public class InterfaceEditRequest implements Serializable {
    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    private static final long serialVersionUID = 1L;
}
