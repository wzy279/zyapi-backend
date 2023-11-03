package com.wzy.zyapi.service.impl;

import com.wzy.zyapi.service.FYService;
import com.wzy.zyapi.utils.BigModelNew;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import javax.annotation.Resource;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-31 20:10
 **/
public class FYServiceImpl implements FYService {

    private Boolean totalFlag = true;

    @Resource
    private BigModelNew bigModelNew;

    public static final String hostUrl = "https://spark-api.xf-yun.com/v2.1/chat";
    public static final String appid = "3c82da55";
    public static final String apiSecret = "NzMyMjNhZjJiZWQwMDIyYTI2Mjk5NjJi";
    public static final String apiKey = "5dee08fd0fe1ae2ea64b267618e11d91";

    @Override
    public String chat(String msg) throws Exception {
        if (totalFlag) {
            System.out.print("我：");
            totalFlag = false;
            String NewQuestion = msg;
            // 构建鉴权url
            String authUrl = bigModelNew.getAuthUrl(hostUrl, apiKey, apiSecret);
            OkHttpClient client = new OkHttpClient.Builder().build();
            String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
            Request request = new Request.Builder().url(url).build();
            for (int i = 0; i < 1; i++) {
                WebSocket webSocket = client.newWebSocket(request, new BigModelNew(i + "", false));
            }
        } else {
            Thread.sleep(200);
        }
        return null;
    }
}
