package com.xiaobei.rabbitmq.javaapi;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq java api 测试用例
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-13 13:44:44
 */
@ExtendWith(SpringExtension.class)
@DisplayName("测试Rabbitmq Java Client 相关功能")
@ContextConfiguration
public class RabbitmqJavaApiTest {

    /**
     * 当前测试类所用全局配置信息
     */
    @Configuration
    @PropertySource(value = "classpath:rabbitmq.properties")
    static class RabbitmqConfig {

        @Value("${rabbitmq.host}")
        private String rabbitmqHost;

        @Value("${rabbitmq.port}")
        private Integer rabbitmqPort;

        @Value("${rabbitmq.username}")
        private String rabbitmqUsername;

        @Value("${rabbitmq.password}")
        private String rabbitmqPassword;

        @Value("${rabbitmq.vhost}")
        private String rabbitmqVHost;

        /**
         * 获取 rabbitmq 连接工厂
         * @return
         */
        @Bean
        public ConnectionFactory factory() {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(rabbitmqHost);
            factory.setPort(rabbitmqPort);
            factory.setVirtualHost(rabbitmqVHost);
            factory.setUsername(rabbitmqUsername);
            factory.setPassword(rabbitmqPassword);
            return factory;
        }
    }

    @Autowired
    private ConnectionFactory factory;

    private static final String SIMPLE_EXCHANGE = "SIMPLE_EXCHANGE";

    private static final String SIMPLE_QUEUE = "SIMPLE_QUEUE";

    private static final String SIMPLE_ROUTING_KEY = "SIMPLE";

    @Test
    void testConnectionFactory() {
        System.out.println(factory);
    }

    @Nested
    @DisplayName("rabbitmq 简单使用示例")
    class Simple {

        /**
         * 初始化 {@link com.rabbitmq.client.impl.AMQImpl.Exchange}
         * 和 {@link com.rabbitmq.client.AMQP.Queue}
         * 并建立绑定关系
         */
        @BeforeEach
        @DisplayName("创建交换机与队列并建立之间的关系")
        public void initExchangeAndQueue() {
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                // 声明交换机，交换机为直连类型
                channel.exchangeDeclare(SIMPLE_EXCHANGE, "direct");
                // 声明队列
                // TODO 各个参数的详细说明？？
                channel.queueDeclare(SIMPLE_QUEUE,
                        false,
                        false,
                        false,
                        null);
                // 交换机与队列建立绑定关系
                channel.queueBind(SIMPLE_QUEUE, SIMPLE_EXCHANGE, SIMPLE_ROUTING_KEY);
            } catch (TimeoutException | IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 发送简单信息
         */
        @Test
        @DisplayName("简单消息发送者示例")
        void simpleSendMessage() {
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                String msg = "hello rabbitmq!";
                // TODO 各个参数的详细说明？？
                channel.basicPublish(SIMPLE_EXCHANGE,
                        SIMPLE_ROUTING_KEY,
                        null,
                        msg.getBytes(StandardCharsets.UTF_8));
            } catch (TimeoutException | IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 简单的消息接收
         */
        @Test
        @DisplayName("简单消费者示例")
        void simpleConsumerMessage() {
            /*
             * 使用 {@code CountDownLatch}，
             * 保证方法 {@code com.rabbitmq.client.DefaultConsumer.handleDelivery} 能够正常结束
             */
            CountDownLatch latch = new CountDownLatch(1);
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.basicConsume(SIMPLE_QUEUE, true,
                        // TODO 各个方法的详细说明？？
                        new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        String message = new String(body, StandardCharsets.UTF_8);
                        System.out.printf("received message: %s%n", message);
                        System.out.printf("consumerTag: %s%n", consumerTag);
                        System.out.printf("deliveryTag: %s%n", envelope.getDeliveryTag());
                        latch.countDown();
                    }
                });
                latch.await();
            } catch (TimeoutException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
