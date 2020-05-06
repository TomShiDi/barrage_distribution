package com.barrage.webcontroller.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/19
 **/
@Component
public class DefaultRedisComponent {
    private StringRedisTemplate stringRedisTemplate;

    private long timeout = 3600;


    @Autowired
    public DefaultRedisComponent(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public Long setAsList(String key, String value) {
        if (value == null || key == null) {
            return null;
        }
        return stringRedisTemplate.opsForList().rightPush(key, value);
    }

    public boolean setAsKeyValue(String key, String value) {
        return setAsKeyValue(key, value, timeout);
    }

    /**
     * 以key-value的形式设置
     * @param key 键
     * @param value 值
     * @param expire 过期时间
     * @return 是否成功
     */
    public boolean setAsKeyValue(String key, String value, long expire) {
        if (value == null || key == null) {
            return false;
        }
        stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 获取key对应的值
     * @param key 键名
     * @return 值
     */
    public String getStringValue(String key) {
        if (key == null) {
            return null;
        }
        String value = stringRedisTemplate.opsForValue().get(key);
        return value;
    }

}
