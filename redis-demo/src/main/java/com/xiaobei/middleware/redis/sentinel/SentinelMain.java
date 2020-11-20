package com.xiaobei.middleware.redis.sentinel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/20 6:45
 */
@Component
@PropertySource(value = "classpath:/META-INF/redis-sentinel.properties")
public class SentinelMain {

    @Value("${spring.redis.sentinel.master}")
    private String name;

    @Value("${spring.redis.sentinel.nodes}")
    private String sentinels;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SentinelMain.class);
        applicationContext.refresh();
        SentinelMain sentinelMain = applicationContext.getBean(SentinelMain.class);
        System.out.println(sentinelMain.name);
        System.out.println(sentinelMain.sentinels);
        applicationContext.close();
    }
}
