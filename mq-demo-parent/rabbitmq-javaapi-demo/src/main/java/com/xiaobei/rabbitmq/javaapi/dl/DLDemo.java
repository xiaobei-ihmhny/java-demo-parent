package com.xiaobei.rabbitmq.javaapi.dl;

import com.rabbitmq.client.*;
import com.xiaobei.rabbitmq.javaapi.utils.ResourceUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列（Dead Letter）示例
 *
 * <ul>什么情况下消息会变成死信？</ul>
 * <ol>1. 消息被消息者拒绝并且设置重回队列</ol>
 * <ol>2. 消息过期</ol>
 * <ol>3. 队列达到最大长度，超过了 Max length(消息数) 或者 Max length bytes（字节数），最先入队的消息会发送到 DLX</ol>
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 13:53:53
 */
public class DLDemo {

    public static final String DL_EXCHANGE_NAME = "dl-exchange";

    public static final String DL_QUEUE_NAME = "dl-queue";

    public static final String ORIGINAL_EXCHANGE_NAME = "original-exchange";

    public static final String ORIGINAL_QUEUE_NAME = "original-queue";

    public static final String ORIGINAL_ROUTING_KEY = "original-queue";

    /**
     * 由于 普通队列 绑定 死信交换机时，并没有路由key，
     * 故，死信交换机绑定死信队列时不能使用 direct 模式。
     * 否则，发向死信交换机的信息无法进行正确路由。
     * @param args
     */
    public static void main(String[] args) {
        // 获取连接工厂
        ConnectionFactory factory = ResourceUtil.newFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 声明一个交换机作为死信交换机
            channel.exchangeDeclare(DL_EXCHANGE_NAME,
                    "direct",
                    true,
                    false,
                    false,
                    null);
            // 声明一个队列作为死信队列
            channel.queueDeclare(DL_QUEUE_NAME,
                    true,
                    false,
                    false,
                    null);
            // 将声明的死信交换机与死信队列进行绑定，与正常队列使用同样的路由key
            channel.queueBind(DL_QUEUE_NAME, DL_EXCHANGE_NAME, ORIGINAL_ROUTING_KEY);
            // 声明一个普通交换机
            channel.exchangeDeclare(ORIGINAL_EXCHANGE_NAME,
                    "direct",
                    true,
                    false,
                    null);
            // 将上面声明的名称为 dl-exchange 的交换机作为当前队列的死信交换机
            Map<String, Object> arguments = new HashMap<>(4);
            arguments.put("x-dead-letter-exchange", "dl-exchange");
            // 设置队列的最大长度为10，超过10个后，队头的消息自动被丢弃
            arguments.put("x-max-length", 10);
            // 声明一个普通的队列并设置死信队列
            channel.queueDeclare(ORIGINAL_QUEUE_NAME,
                    true,
                    false,
                    false,
                    arguments);
            // 将交换机 original-exchange 与 队列 original-queue 进行绑定
            channel.queueBind(ORIGINAL_QUEUE_NAME, ORIGINAL_EXCHANGE_NAME, ORIGINAL_ROUTING_KEY);

            //============== send message =============
//            sendIncludeTTLMessage(channel);
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送带有过期时间的消息
     * @param channel
     * @throws IOException
     */
    private static void sendIncludeTTLMessage(Channel channel) throws IOException {
        // 向 original-exchange 发送路由key 为 original-queue 的包含过期时间的消息
        AMQP.BasicProperties properties =
                new AMQP.BasicProperties.Builder()
                        // 过期时间为 10s
                        .expiration("10000")
                        // 持久化消息
                        .deliveryMode(2)
                        .contentEncoding("UTF-8")
                        .build();
        channel.basicPublish(ORIGINAL_EXCHANGE_NAME,
                ORIGINAL_ROUTING_KEY,
                properties,
                "测试死信队列".getBytes(StandardCharsets.UTF_8));
    }
}
