package com.wzy.zyapi.model.dto.interfaceinfo;

import com.wzy.zycommon.model.entity.InterfaceInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-03 20:39
 **/
// todo 取消注释开启 ES（须先配置 ES）  post为索引名字
//@Document(indexName = "InterfaceInfo")
@Data
public class InterfaceEsDTO implements Serializable {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    @Id
    private long id;

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
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date createTime;


    /**
     * 更新时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date updateTime;

    /**
     * 是否删除
     */
    private Byte isDelete;


    /**
     * 对象转包装类
     *
     * @param interfaceInfo
     * @return
     */
    public static InterfaceEsDTO objToDto(InterfaceInfo interfaceInfo) {
        if (interfaceInfo == null) {
            return null;
        }
        InterfaceEsDTO interfaceEsDTO = new InterfaceEsDTO();
        BeanUtils.copyProperties(interfaceInfo, interfaceEsDTO);
        return interfaceEsDTO;
    }

    /**
     * 包装类转对象
     *
     * @param interfaceEsDTO
     * @return
     */
    public static InterfaceInfo dtoToObj(InterfaceEsDTO interfaceEsDTO) {
        if (interfaceEsDTO == null) {
            return null;
        }
        InterfaceInfo interfaceInfo =new InterfaceInfo();
        BeanUtils.copyProperties(interfaceEsDTO, interfaceInfo);
        return interfaceInfo;
    }

}
