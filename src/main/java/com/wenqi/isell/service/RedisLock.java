package com.wenqi.isell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author: 文琪
 * @Description: redis锁
 * @Date: Created in 2018/4/3 下午5:02
 * @Modified By:
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value){
        if (redisTemplate.opsForValue().setIfAbsent(key, value))
            return true;
        // 多个线程同时请求 只会让一个线程拿到锁
        String currentValue = redisTemplate.opsForValue().get(key);
        // 如果锁过期了
        if (!StringUtils.isEmpty(currentValue) &&Long.parseLong(currentValue) < System.currentTimeMillis()){
          //获取上一个锁的时间
           String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
           if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue))
               return true;
        }

        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value){
        try{
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value))
                redisTemplate.opsForValue().getOperations().delete(key);
        }catch (Exception e){
            log.error("【redis分布式锁】解锁异常,{}",e);
        }


    }
}
