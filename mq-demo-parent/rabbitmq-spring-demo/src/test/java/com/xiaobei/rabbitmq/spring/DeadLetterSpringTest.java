package com.xiaobei.rabbitmq.spring;

import com.xiaobei.rabbitmq.spring.dl.DeadLetterSample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;

/**
 * 使用 spring 的方式测试 DeadLetter
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-18 06:48:48
 */
@ExtendWith(SpringExtension.class)
@DisplayName("在 Spring 中使用 dead-letter 示例")
@ContextConfiguration(classes = DeadLetterSample.DeadLetterConfig.class)
public class DeadLetterSpringTest {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Test
    @DisplayName("验证消息超时时，是否会变为死信消息")
    void messageTimeout() {
        MessageProperties properties = new MessageProperties();
        // 设置消息10s后过期
        properties.setExpiration("10000");
        // 持久化消息
        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = new Message("消息将在 10s 钟后过期".getBytes(StandardCharsets.UTF_8), properties);
        rabbitTemplate.convertAndSend(DeadLetterSample.DeadLetterConfig.QUEUE_NAME, message);
    }

}
