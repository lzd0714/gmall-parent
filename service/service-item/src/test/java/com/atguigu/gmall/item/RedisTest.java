package com.atguigu.gmall.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-02 21:26
 **/
@SpringBootTest
public class RedisTest {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Test
    void redisTest(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("hello","world");
        System.out.println("redis保存完成");
        String hello = ops.get("hello");
        System.out.println(hello);


    }
}
