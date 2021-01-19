package com.xiaobei.rabbitmq.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

/**
 * RabbitAdmin 的作用 示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-19 06:16:16
 */
@DisplayName("RabbitAdmin")
public class RabbitAdminTest {

    @Test
    @DisplayName("基础用法")
    void usage() {
        ConnectionFactory connectionFactory = new CachingConnectionFactory();
        AmqpAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 声明一个直连类型的交换机
        rabbitAdmin.declareExchange(new DirectExchange("directExchange",
                false,
                false));
        // 声明一个队列
        rabbitAdmin.declareQueue(new Queue("queue",
                false,
                false,
                false));
        // 声明一个绑定关系
        rabbitAdmin.declareBinding(new Binding("binding",
                Binding.DestinationType.QUEUE,
                "direcrtExchange",
                "queue",
                null));
    }
}
