package org.example.app.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.example.app.service.IChatService;
import org.example.app.service.IRedisService;
import org.example.common.util.HttpUtil;
import org.example.common.util.JsonUtil;
import org.example.common.util.MyErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class QianFanServiceImpl implements IChatService {

    @Autowired
    private QianFanConfig baiduChatConfig;

    @Autowired
    private IRedisService redisService;


    /**
     * 获取令牌
     *
     * @return {@link String }
     */
    private String getToken() {
        // 获取token 缓存20天, 百度服务器最长有效期30天
        return redisService.getAndSet("baiduChatToken", 86400 * 20, () -> {
            String tokenUrl = String.format("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s", baiduChatConfig.getAppKey(), baiduChatConfig.getSecretKey());
            try {
                String s = new HttpUtil(tokenUrl).get();
                Map<String, Object> map = JsonUtil.getMap(s);
                Object o = map.get("access_token");
                if (o == null) {
                    String errMsg = "获取百度token失败: " + s;
                    log.error(errMsg);
                    MyErrorUtil.throwE(errMsg);
                } else {
                    String token = o.toString();
                    log.info("获取百度token成功: " + token);
                    return token;
                }
            } catch (Exception e) {
                MyErrorUtil.throwE("获取百度token失败");
            }
            return null;
        });
    }

    /**
     * 发送消息
     *
     * @param msg msg
     * @return {@link String }
     */
    @Override
    public String sendMsg(String msg) {
        log.info("接收到用户消息: " + msg);
        try {
            // 遍历模型, 判断是否超过频率限制
            for (QianFanConfig.BaiduModel model : baiduChatConfig.getModels()) {
                String modelName = model.getModelName();
                // 按顺序遍历模型, 如果超过频率限制，则跳过
                if (redisService.isRateOk(modelName, model.getNumberVisits(), model.getTimeInSeconds())) {
                    log.info(String.format("模型：%s；msg: %s", modelName, msg)); // 记录访问信息
                    return sendMsgByModel(msg, modelName);
                }
            }
            log.error("频率超过限制"); //  应该有消息预警
            return "频率超过限制，请稍后再试";

        } catch (Exception e) {
            log.error("发送消息失败: " + e.getMessage());
            MyErrorUtil.throwE("发送消息失败"); // 抛出异常，不继续往下执行
            return null;
        }
    }

    /**
     * 使用指定模型发送消息
     *
     * @param msg       msg
     * @param modelName 型号名称
     * @return {@link String }
     * @throws Exception 例外
     */
    public String sendMsgByModel(String msg, String modelName) throws Exception {
        String url = String.format("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/%s?access_token=%s", modelName, getToken());


        //   {
        //        "messages": [
        //        {
        //            "role": "user",
        //                "content": "已知山药可以明目，已知黄连可以明目，根据上述信息，请问什么明目？不要解释，直接回答"
        //        }
        //]
        //    }
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>();
        HashMap<String, Object> chatMap = new HashMap<>();
        chatMap.put("role", "user");
        chatMap.put("content", msg);
        hashMaps.add(chatMap);
        map.put("messages", hashMaps);


        String s = new HttpUtil(url).postJson(map);
        Map<String, Object> res = JsonUtil.getMap(s);
        // 异常情况
        if (res.containsKey("error_code")) {
            log.error("发送消息失败: " + res.get("error_msg").toString()); // 预警
            MyErrorUtil.throwE(res.get("error_msg").toString());
        }

        Object o = res.get("result");
        return o.toString();
    }


}
