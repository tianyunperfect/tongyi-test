package org.example.app.service;

import java.util.function.Supplier;

public interface IRedisService {

    /**
     * 获取value 并设置过期时间
     *
     * @param key                key
     * @param cacheTimeInSeconds 缓存时间（秒）
     * @param supplier           匿名函数
     * @return {@link String }
     */
    String getAndSet(String key, long cacheTimeInSeconds, Supplier<String> supplier);

    /**
     * 访问频率是否正常
     *
     * @param key            key
     * @param maxAccessCount 最大访问次数
     * @param accessTime     缓存时间
     * @return boolean
     */
    boolean isRateOk(String key, int maxAccessCount, long accessTime);
}
