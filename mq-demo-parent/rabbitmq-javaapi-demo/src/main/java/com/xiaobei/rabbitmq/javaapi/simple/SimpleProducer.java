package com.xiaobei.rabbitmq.javaapi.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xiaobei.rabbitmq.javaapi.utils.ResourceUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * TODO
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-13 06:42:42
 */
public class SimpleProducer {

    public static void main(String[] args) {
        // 配置连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ResourceUtil.getKey("rabbitmq.host"));
        factory.setPort(Integer.parseInt(ResourceUtil.getKey("rabbitmq.port")));
        factory.setVirtualHost(ResourceUtil.getKey("rabbitmq.vhost"));
        factory.setUsername(ResourceUtil.getKey("rabbitmq.username"));
        factory.setPassword(ResourceUtil.getKey("rabbitmq.password"));
        try (Connection connection = factory.newConnection();// 创建连接
             Channel channel = connection.createChannel()) {// 创建 Channel
            // 发送消息
            channel.basicPublish(
                    "SIMPLE_EXCHANGE",
                    "simple",
                    null,
                    "hello rabbitmq, my name is producer".getBytes(StandardCharsets.UTF_8));
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }

    }
}
