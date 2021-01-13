package com.xiaobei.rabbitmq.javaapi.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xiaobei.rabbitmq.javaapi.utils.ResourceUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq ttl 服务端示例
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-12 11:56:56
 */
@SuppressWarnings("all")
public class TTLProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 构建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(ResourceUtil.getKey("rabbitmq.username"));
        factory.setPassword(ResourceUtil.getKey("rabbitmq.password"));
        factory.setHost(ResourceUtil.getKey("rabbitmq.host"));
        factory.setPort(Integer.parseInt(ResourceUtil.getKey("rabbitmq.port")));
        factory.setVirtualHost(ResourceUtil.getKey("rabbitmq.vhost"));

        try (Connection connection = factory.newConnection();// 建立连接
             Channel channel = connection.createChannel()) {// 创建消息通道
            String msg = "Hello Rabbitmq";
            // 通过队列属性设置消息过期时间
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("x-message-ttl", 6000);
            // 声明队列
            channel.queueDeclare("TEST_TTL_QUEUE", // 队列名称
                    false, // 是否持久化，设置为 true 时 队列在服务器重启后不会丢失
                    false,// 队列是否为独占队列，TODO 即仅此连接可用？？
                    false,// 是否在队列不使用时自动删除队列
                    arguments);// TODO 队列的其他属性信息，可以有哪些可用的属性呢？
            // 对每条消息设置过期时间
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)// 持久化消息
                    .contentEncoding("UTF-8")
                    .expiration("10000") // TTL
                    .build();
            // 当对队列和消息同时设置过期时间时，将以较小的为准
            // 发送消息
            channel.basicPublish("", "TEST_DLX_QUEUE", properties, msg.getBytes(StandardCharsets.UTF_8));
        }

    }

}
