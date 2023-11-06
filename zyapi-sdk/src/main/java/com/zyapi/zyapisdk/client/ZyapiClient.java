package com.zyapi.zyapisdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zyapi.zyapisdk.model.User;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.zyapi.zyapisdk.util.SignUtil.getSign;

/**
 * @description 通用第三方接口
 * @author: Wangzhaoyi
 * @create: 2023-10-02 12:56
 **/
public class ZyapiClient {
    private static String LOCALHOST = "http://localhost:8090/api/";

    private String accesskey;
    private String secretKey;

    public ZyapiClient(String accesskey, String secretKey) {
        this.accesskey = accesskey;
        this.secretKey = secretKey;
    }

    public String getname(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "这是名字");
        String result = HttpUtil.get(LOCALHOST + "name/get/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getnamepost(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "这是名字");
        String result = HttpUtil.post(LOCALHOST + "name/post", paramMap);
        System.out.println(result);
        return result;
    }

    private Map<String, String> getHeader(String body) {
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("accesskey", accesskey);
        hashmap.put("body", body);
        hashmap.put("sign", getSign(body, secretKey));
        return hashmap;
    }

    public String getnamepost2(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse execute = HttpRequest.post(LOCALHOST + "name/post2")
                .charset(StandardCharsets.UTF_8)
                .addHeaders(getHeader(json))
                .body(json)
                .execute();
        System.out.println(execute.getStatus());
        String result = execute.body();
        System.out.println(result);
        return result;
    }

    public String README() {
        String message = "welcome to WZY's First SDK";
        return message;
    }

    /**
     * POST通用请求
     *
     * @param host  请求路径URL
     * @param param 请求参数
     * @return 响应值
     */
    public <T> String POSTgenericRequest(String host, Map<String, Object> param) {
        String result = null;
        //POST方法
        String json = JSONUtil.toJsonStr(param);
        HttpResponse execute = HttpRequest.post(host)
                .charset(StandardCharsets.UTF_8)
                .addHeaders(getHeader(json))
                .body(json)
                .execute();
        System.out.println(execute.getStatus());
        result = execute.body();
        System.out.println(result);
        return result;
    }


    /**
     * GET通用请求
     *
     * @param host  请求路径URL
     * @param param 请求参数
     * @return 响应值
     */
    public <T> String GETgenericRequest(String host, Map<String, Object> param) {
        String json = JSONUtil.toJsonStr(param);
        host = splicingGetRequest(host, param);
        String result = HttpRequest.get(host).addHeaders(getHeader(json)).execute().body();
        System.out.println(result);
        return result;
    }

    /**
     * 拼接 GET请求参数
     *
     * @param host
     * @param param
     * @return
     */
    private String splicingGetRequest(String host, Map<String, Object> param) {
        StringBuilder urlBuilder = new StringBuilder(host);
        // urlBuilder最后是/结尾且path以/开头的情况下，去掉urlBuilder结尾的/
        if (urlBuilder.toString().endsWith("/")) {
            urlBuilder.setLength(urlBuilder.length() - 1);
        }
        urlBuilder.append("?");
        for (Map.Entry<String,Object> entry: param.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            urlBuilder.append(key).append("=").append(value).append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        return urlBuilder.toString();
    }
}
