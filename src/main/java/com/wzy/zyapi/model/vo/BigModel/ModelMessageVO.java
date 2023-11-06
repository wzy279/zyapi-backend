package com.wzy.zyapi.model.vo.BigModel;

import com.wzy.zyapi.model.BigModel.RoleContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-05 08:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelMessageVO implements Serializable {
    public static final String ROLE_USER = "user";

    public static final String ROLE_ASSISTANT = "assistant";
    /**
     * 消息来源方
     */
    private String role;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建用户消息
     * @param content
     * @return
     */
    public static RoleContent createUserRoleContent(String content) {
        return new RoleContent(ROLE_USER, content);
    }

    /**
     * 创建AI消息内容
     * @param content
     * @return
     */
    public static RoleContent createAssistantRoleContent(String content) {
        return new RoleContent(ROLE_ASSISTANT, content);
    }
}
