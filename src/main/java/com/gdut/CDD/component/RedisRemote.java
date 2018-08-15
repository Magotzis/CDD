package com.gdut.CDD.component;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author dengyq on 2018/8/14 上午10:06
 */
@Component
public class RedisRemote {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean setNx(String key, String value) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        return ops.setIfAbsent(key, value);
    }

    public void expire(String key, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.expire(key, timeout, timeUnit);
    }

    public void set(String key, String value) {
        //更改在redis里面查看key编码问题
        RedisSerializer redisSerializer = new StringRedisSerializer();
        stringRedisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        vo.set(key, value);
    }

    public Object get(String key) {
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        return vo.get(key);
    }

}
