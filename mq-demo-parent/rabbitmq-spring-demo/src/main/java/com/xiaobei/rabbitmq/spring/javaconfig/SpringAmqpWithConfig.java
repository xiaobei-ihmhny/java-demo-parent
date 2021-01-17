package com.xiaobei.rabbitmq.spring.javaconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * java configuration 方式配置 spring amqp 示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 07:05:05
 */
public class SpringAmqpWithConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringAmqpWithConfig.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        applicationContext.register(AmqpConfig.class);
        // 启动 spring 应用上下文
        applicationContext.refresh();
        AmqpTemplate template = applicationContext.getBean(AmqpTemplate.class);
        // 发送消息
        template.convertAndSend("my-queue", "foo");
        // 接收消息
        String foo = (String) template.receiveAndConvert("my-queue");
        LOGGER.info("获取到路由键 my-queue 的消息为：{}", foo);
        // 关闭 spring 应用上下文
        applicationContext.close();
    }
}
