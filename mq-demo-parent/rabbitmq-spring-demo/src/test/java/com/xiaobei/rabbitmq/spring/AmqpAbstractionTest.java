package com.xiaobei.rabbitmq.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;

/**
 * AMQP 抽象
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 10:39:39
 */
@DisplayName("amqp 抽象")
public class AmqpAbstractionTest {

    /**
     * 参考：https://docs.spring.io/spring-amqp/docs/current/reference/html/#binding
     */
    @Test
    void binding() {
//        new Binding(new Queue("someQueue"), new DirectExchange("someDirectExchange"), "foo.bar");
        // 直连交换机绑定 DirectExchange
        Binding directBinding = BindingBuilder
                .bind(new Queue("someQueue"))
                .to(new DirectExchange("someDirectExchange"))
                .with("foo.bar");
        // 主题交换机绑定 TopicExchange
        Binding topicBinding = BindingBuilder
                .bind(new Queue("someQueue"))
                .to(new TopicExchange("someTopicExchange"))
                .with("foo.*");
        // 广播类型交换机 FanoutExchange
        Binding fanoutBinding = BindingBuilder
                .bind(new Queue("someQueue"))
                .to(new FanoutExchange("someFanoutExchange"));
    }
}
