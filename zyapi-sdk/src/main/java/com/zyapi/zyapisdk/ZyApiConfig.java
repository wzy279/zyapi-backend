package com.zyapi.zyapisdk;

import com.zyapi.zyapisdk.client.ZyapiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-02 15:20
 **/
@Configuration
@ConfigurationProperties("zyapi.client")
@Data
@ComponentScan
public class ZyApiConfig {
    private String accesskey;
    private String secretkey;

    @Bean
    public ZyapiClient zyapiClient(){
        return new ZyapiClient(accesskey,secretkey);
    }
}
