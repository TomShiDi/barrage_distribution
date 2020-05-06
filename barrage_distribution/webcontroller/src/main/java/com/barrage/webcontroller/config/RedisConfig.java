package com.barrage.webcontroller.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/4/19
 **/
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean(name = "keyGenerator")
    public KeyGenerator keyGenerators() {
        return (target, method, params) -> {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(target.getClass().getName());
            stringBuffer.append(method.getName());
            for (Object o : params) {
                if (o != null) {
                    stringBuffer.append(o.toString());
                }
            }
            return stringBuffer.toString();
        };
    }

    @Bean(name = "cacheManager")
    public CacheManager cacheManagers(RedisTemplate redisTemplate) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60 * 20))
                .disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager
                .builder(redisTemplate.getConnectionFactory())
                .cacheDefaults(configuration)
                .transactionAware()
                .build();
        return cacheManager;
    }

    @Primary
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        setValueSerializer(redisTemplate);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private void setValueSerializer(RedisTemplate redisTemplate) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    }
}
