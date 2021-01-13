package com.xiaobei.rabbitmq.javaapi.simple;

import com.rabbitmq.client.*;
import com.xiaobei.rabbitmq.javaapi.utils.ResourceUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq 消息接收示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-13 06:42:42
 */
public class SimpleConsumer {

    public static void main(String[] args) {
        // 配置连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ResourceUtil.getKey("rabbitmq.host"));
        factory.setPort(Integer.parseInt(ResourceUtil.getKey("rabbitmq.port")));
        factory.setUsername(ResourceUtil.getKey("rabbitmq.username"));
        factory.setPassword(ResourceUtil.getKey("rabbitmq.password"));
        factory.setVirtualHost(ResourceUtil.getKey("rabbitmq.vhost"));

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            // 声明交换机
            channel.exchangeDeclare("SIMPLE_EXCHANGE", "direct", false, false, null);
            // 声明队列
            channel.queueDeclare("SIMPLE_QUEUE", false, false, false, null);
            // 绑定交换机与队列
            channel.queueBind("SIMPLE_QUEUE", "SIMPLE_EXCHANGE", "simple");
            // 创建消费者
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.printf("received message: %s%n", message);
                    System.out.printf("consumerTag: %s%n", consumerTag);
                    System.out.printf("deliveryTag: %s%n", envelope.getDeliveryTag());
                }
            };

            // 开始获取消息
            channel.basicConsume("SIMPLE_QUEUE", true, consumer);
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
