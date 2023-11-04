package com.wzy.zyapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzy.zyapi.common.BaseResponse;
import com.wzy.zyapi.common.ErrorCode;
import com.wzy.zyapi.common.ResultUtils;
import com.wzy.zyapi.config.XFConfig;
import com.wzy.zyapi.exception.BusinessException;
import com.wzy.zyapi.exception.ThrowUtils;
import com.wzy.zyapi.listener.XFWebClient;
import com.wzy.zyapi.listener.XFWebSocketListener;
import com.wzy.zyapi.mapper.ModelInfoMapper;
import com.wzy.zyapi.model.BigModel.NettyGroup;
import com.wzy.zyapi.model.BigModel.ResultBean;
import com.wzy.zyapi.model.BigModel.RoleContent;
import com.wzy.zyapi.model.entity.ModelInfo;
import com.wzy.zyapi.service.PushService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-04 10:50
 **/
@Slf4j
@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private XFConfig xfConfig;

    @Autowired
    private XFWebClient xfWebClient;

    @Resource
    private ModelInfoMapper modelInfoMapper;

    @Override
    public void pushToOne(String uid, String text) {
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(text)) {
            log.error("uid或text均不能为空");
            throw new RuntimeException("uid或text均不能为空");
        }
        ConcurrentHashMap<String, Channel> userChannelMap = NettyGroup.getUserChannelMap();
        for (String channelId : userChannelMap.keySet()) {
            if (channelId.equals(uid)) {
                Channel channel = userChannelMap.get(channelId);
                if (channel != null) {
                    ResultBean success = ResultBean.success(text);
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(success)));
                    log.info("信息发送成功：{}", JSON.toJSONString(success));
                } else {
                    log.error("该id对于channelId不存在！");
                }
                return;
            }
        }
        log.error("该用户不存在！");
    }

    @Override
    public void pushToAll(String text) {
        String trim = text.trim();
        ResultBean success = ResultBean.success(trim);
        NettyGroup.getChannelGroup().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(success)));
        log.info("信息推送成功：{}", JSON.toJSONString(success));
    }

    //测试账号只有2个并发，此处只使用一个，若是生产环境允许多个并发，可以采用分布式锁
    @Override
    public synchronized BaseResponse<String> pushMessageToXFServer(Long id, String uid, String text) {
        QueryWrapper<ModelInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("user_id",uid);
        ModelInfo modelInfo = modelInfoMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(modelInfo==null,ErrorCode.NOT_FOUND_ERROR);
        //TODO 修改这个question可以把历史记录一起传过去
        ArrayList<RoleContent> questions = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(modelInfo.getMessage());
        if(jsonArray!=null){
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RoleContent roleContent = jsonObject.toJavaObject(RoleContent.class);
                questions.add(roleContent);
            }
        }
        RoleContent userRoleContent = RoleContent.createUserRoleContent(text);
        questions.add(userRoleContent);
        XFWebSocketListener xfWebSocketListener = new XFWebSocketListener();
        WebSocket webSocket = xfWebClient.sendMsg(uid, questions, xfWebSocketListener);
        if (webSocket == null) {
            log.error("webSocket连接异常");
            ResultBean.fail("请求异常，请联系管理员");
        }
        try {
            int count = 0;
            int maxCount = xfConfig.getMaxResponseTime() * 5;
            while (count <= maxCount) {
                Thread.sleep(200);
                if (xfWebSocketListener.isWsCloseFlag()) {
                    break;
                }
                count++;
            }
            if (count > maxCount) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            RoleContent answer = RoleContent.createAssistantRoleContent(xfWebSocketListener.getAnswer());
            jsonArray.add(userRoleContent);
            jsonArray.add(answer);
            String message = jsonArray.toJSONString();
            ModelInfo modelInfo1 = new ModelInfo();
            modelInfo1.setId(id);
            modelInfo1.setMessage(message);
            modelInfoMapper.updateById(modelInfo1);
            return ResultUtils.success(xfWebSocketListener.getAnswer());
        } catch (Exception e) {
            log.error("请求异常：{}", e);
        } finally {
            webSocket.close(1000, "");
        }
        return ResultUtils.success("");
    }
}
