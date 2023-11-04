package com.wzy.zyapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-04 11:03
 **/
@Data
@Component
@ConfigurationProperties("xf.config")
public class XFConfig {

    private String appId;

    private String apiSecret;

    private String apiKey;

    private String hostUrl;

    private Integer maxResponseTime;

}
