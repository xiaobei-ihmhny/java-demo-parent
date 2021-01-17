package com.xiaobei.rabbitmq.spring.dl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.charset.StandardCharsets;

/**
 * spring 方式使用死信队列示例
 *
 * <ul>什么情况下消息会变成死信？</ul>
 * <ol>1. 消息被消息者拒绝并且设置重回队列</ol>
 * <ol>2. 消息过期</ol>
 * <ol>3. 队列达到最大长度，超过了 Max length(消息数) 或者 Max length bytes（字节数），最先入队的消息会发送到 DLX</ol>
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 18:16:16
 */
@DisplayName("示例如何 rabbitmq 中的死信队列")
public class DeadLetterSample {

    @Configuration
    @PropertySource(value = "classpath:META-INF/rabbitmq.properties")
    static class DeadLetterConfig {

        //=================== rabbitmq common config start ====================
        @Bean(name = "factory")
        public ConnectionFactory factory(@Value("${rabbitmq.host}") String host,
                                         @Value("${rabbitmq.port}") Integer port,
                                         @Value("${rabbitmq.vhost}") String vhost,
                                         @Value("${rabbitmq.username}") String username,
                                         @Value("${rabbitmq.password}") String password) {
            CachingConnectionFactory factory = new CachingConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setVirtualHost(vhost);
            return factory;
        }

        @Bean(name = "rabbitTemplate")
        public AmqpTemplate rabbitTemplate(ConnectionFactory factory) {
            return new RabbitTemplate(factory);
        }

        @Bean(name = "rabbitAdmin")
        public AmqpAdmin rabbitAdmin(ConnectionFactory factory) {
            return new RabbitAdmin(factory);
        }
        //=================== rabbitmq common config end ====================

        //=================== dead letter config start ====================

        @Bean(name = "dlExchange")
        public TopicExchange dlExchange() {
            return new TopicExchange("dl-exchange-spring",
                    true,
                    false);
        }

        @Bean(name = "dlQueue")
        public Queue dlQueue() {
            return new Queue("dl-queue-spring");
        }

        @Bean(name = "dlBinding")
        public Binding dlBinding() {
            return BindingBuilder
                    .bind(dlQueue())
                    .to(dlExchange())
                    .with("#");
        }
        //=================== dead letter config end ====================

        public static final String EXCHANGE_NAME = "original-exchange-spring";

        public static final String QUEUE_NAME = "original-queue-spring";

        //=================== common config start ====================
        @Bean(name = "exchange")
        public DirectExchange exchange() {
            return new DirectExchange(EXCHANGE_NAME,
                    true,
                    false);
        }

        @Bean(name = "queue")
        public Queue queue() {
            Queue queue = new Queue(QUEUE_NAME);
            // 为当前的 queue 添加指定的交换机作为其死信交换机
            queue.addArgument("x-dead-letter-exchange", "dl-exchange-spring");
            return queue;
        }

        @Bean(name = "binding")
        public Binding binding() {
            return BindingBuilder
                    .bind(queue())
                    .to(exchange())
                    .with(QUEUE_NAME);
        }
        //=================== common config end ====================
    }

    @Test
    @DisplayName("向指定有死信交换机的普通队列发送带有过期时间的消息")
    void sendTTLMessageToCommonQueueWithDLX() {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        applicationContext.register(DeadLetterConfig.class);
        applicationContext.refresh();
        AmqpTemplate amqpTemplate = applicationContext.getBean(AmqpTemplate.class);
        MessageProperties properties = new MessageProperties();
        properties.setExpiration("10000");// 设置其过期时间为10s
        Message message = new Message("spring方式测试带过期时间的消息".getBytes(StandardCharsets.UTF_8), properties);
        amqpTemplate.convertAndSend(DeadLetterConfig.EXCHANGE_NAME, DeadLetterConfig.QUEUE_NAME, message);
        applicationContext.close();
    }
}
