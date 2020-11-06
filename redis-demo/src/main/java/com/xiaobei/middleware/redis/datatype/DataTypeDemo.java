package com.xiaobei.middleware.redis.datatype;

import com.xiaobei.middleware.redis.util.RedisService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/6 22:58
 */
@Configuration
@ComponentScan("com.xiaobei.middleware.redis.util")
public class DataTypeDemo {

    AnnotationConfigApplicationContext context;

    private RedisService redisService;

    @Before
    public void before() {
        context = new AnnotationConfigApplicationContext();
        context.register(DataTypeDemo.class);
        context.refresh();
        redisService = context.getBean(RedisService.class);
    }

    @After
    public void after() {
        context.close();
    }

    @Test
    public void string() {
        redisService.set("111","aaa");
        String s = redisService.get("111");
        System.out.println(s);
    }
}
