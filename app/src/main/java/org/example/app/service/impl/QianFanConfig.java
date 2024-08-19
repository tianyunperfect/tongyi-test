package org.example.app.service.impl;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("tongyi") // 通义千问
public class QianFanConfig {
    private String appId;
    private String appKey;
    private String secretKey;
    private BaiduModel[] models;


    @Data
    public static class BaiduModel {
        //modelName: gpt-3.5-turbo  模型名称
        //timeInSeconds: 1          时间频率秒
        //numberVisits: 30          访问次数 N秒内访问次数

        private String modelName;
        private int timeInSeconds;
        private int numberVisits;
    }
}
