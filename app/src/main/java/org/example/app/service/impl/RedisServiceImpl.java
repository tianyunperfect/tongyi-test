package org.example.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.app.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
@Slf4j
public class RedisServiceImpl implements IRedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * set value with expireTime
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(final String key, String value, Long expireTime) {
        boolean result = false;
        try {
            stringRedisTemplate.opsForValue().set(key, (String) value, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("set error: key {}, value {},expireTime {}", key, value, expireTime, e);
        }
        return result;
    }


    /**
     * key是否存在
     *
     * @param key 钥匙
     * @return boolean
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * get value
     *
     * @param key 钥匙
     * @return {@link String }
     */
    public String get(final String key) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * remove single key
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    DefaultRedisScript<String> redisScript = null;

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("lua/rate_limit.lua"));
        redisScript.setResultType(String.class);


        // --------------[ 对象序列化 start ]--------------
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // --------------[ 对象序列化 end ]--------------

        stringRedisTemplate.setKeySerializer(stringRedisSerializer);
        GenericToStringSerializer genericToStringSerializer = new GenericToStringSerializer(Object.class);
        stringRedisTemplate.setValueSerializer(genericToStringSerializer);
    }

    public boolean isRateOk(String redisKey, int maxAccessCount, long accessTime) {
        // 获取当前时间
        long currentTime = System.currentTimeMillis();

        // 执行 Lua 脚本
        String result = (String) redisTemplate.execute(redisScript, new StringRedisSerializer(), new StringRedisSerializer(),
                Collections.singletonList(redisKey), maxAccessCount + "", currentTime + "", accessTime + "");

        // 根据返回值判断是否超过访问频率限制
        return "OK".equals(result);
    }


    public String getAndSet(String key, long cacheTimeInSeconds, Supplier<String> supplier) {
        String value = get(key);
        if (value != null) {
            return value;
        }
        synchronized (key) {
            value = get(key);
            if (value != null) {
                return value;
            }
            value = supplier.get();
            if (value != null) {
                set(key, value, cacheTimeInSeconds);
            }
            return value;
        }
    }


}
