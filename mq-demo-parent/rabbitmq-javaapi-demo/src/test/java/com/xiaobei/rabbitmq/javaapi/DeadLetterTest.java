package com.xiaobei.rabbitmq.javaapi;

import com.rabbitmq.client.*;
import com.xiaobei.rabbitmq.javaapi.dl.DLDemo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

/**
 * 测试死信消息的产生的几种情况
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 18:49:49
 */
@Nested
@DisplayName("测试产生死信消息的情况")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RabbitmqJavaApiTest.RabbitmqConfig.class)
public class DeadLetterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeadLetterTest.class);

    @Autowired
    private ConnectionFactory connectionFactory;

    @BeforeAll
    public static void initDeadLetter() {
        // 初始化相关的交换机及队列
        DLDemo.main(null);
    }

    @Test
    @DisplayName("验证消息超时时，是否会变为死信消息")
    void messageTimeout() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // 发送带有过期时间的消息
            AMQP.BasicProperties properties =
                    new AMQP.BasicProperties().builder()
                            .expiration("10000")
                            .deliveryMode(2) // 2 表示持久消息 TODO 相关说明在哪儿？
                            .build();
            channel.basicPublish(DLDemo.ORIGINAL_EXCHANGE_NAME,
                    DLDemo.ORIGINAL_ROUTING_KEY,
                    properties,
                    "消息将在 10s 钟后过期".getBytes(StandardCharsets.UTF_8));
            DefaultConsumer callback = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    LOGGER.info("received message: {}", message);
                    LOGGER.info("exchange: {}", envelope.getExchange());
                    LOGGER.info("routingKey: {}", envelope.getRoutingKey());
                }
            };
            // 消费者监听死信队列
            channel.basicConsume(DLDemo.DL_QUEUE_NAME, callback);
            // 阻塞当前进程
            countDownLatch.await();
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 其中 {@link Channel#basicReject(long, boolean) channel.basicReject(deliveryTag, false)} 和
     * {@link Channel#basicNack(long, boolean, boolean) channel.basicNack(deliveryTag, false, false)}
     * 可以达到同样的效果
     */
    @Test
    @DisplayName("验证消息被拒收若未确认并不再重新入队时，是否会变为死信消息")
    void messageRejectOrNAckWithRequeueFalse() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // 发送带有过期时间的消息
            AMQP.BasicProperties properties =
                    new AMQP.BasicProperties().builder()
                            .deliveryMode(2) // 2 表示持久消息 TODO 相关说明在哪儿？
                            .build();
            channel.basicPublish(DLDemo.ORIGINAL_EXCHANGE_NAME,
                    DLDemo.ORIGINAL_ROUTING_KEY,
                    properties,
                    "消息被当前消费者拒收并不再入队".getBytes(StandardCharsets.UTF_8));
            DefaultConsumer callback = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    String exchange = envelope.getExchange();
                    long deliveryTag = envelope.getDeliveryTag();
                    String routingKey = envelope.getRoutingKey();
                    LOGGER.info("received message: {}", message);
                    LOGGER.info("exchange: {}", exchange);
                    LOGGER.info("routingKey: {}", routingKey);
                    if (DLDemo.ORIGINAL_ROUTING_KEY.equals(routingKey)
                            && DLDemo.DL_EXCHANGE_NAME.equals(exchange)) {
                        // 说明当前消息来源 死信队列
                        LOGGER.info("死信队列收到消息，并开始确认消息...");
                        channel.basicAck(deliveryTag, false);
                        LOGGER.info("死信队列已确认消息。");
                        countDownLatch.countDown();
                    } else if (DLDemo.ORIGINAL_ROUTING_KEY.equals(routingKey)
                            && DLDemo.ORIGINAL_EXCHANGE_NAME.equals(exchange)) {
                        // 说明当前消息来源 普通队列
                        /*
                         * 其中 channel.basicReject(deliveryTag, false);
                         * 或者 channel.basicNack(deliveryTag, false, false);
                         * 可以达到同样的效果
                         */
                        channel.basicReject(deliveryTag, false);
//                        channel.basicNack(deliveryTag, false, false);
                        LOGGER.info("拒绝当前消息，并不再入队");
                        countDownLatch.countDown();
                    } else {
                        // do nothing
                    }
                }
            };
            // 消费者监听普通队列
            channel.basicConsume(DLDemo.ORIGINAL_QUEUE_NAME, callback);
            // 消费者监听死信队列
            channel.basicConsume(DLDemo.DL_QUEUE_NAME, callback);
            // 阻塞当前进程
            countDownLatch.await();
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("当队列中消息达到最大长度（x-max-length）" +
            "或最大容量（x-max-length-bytes）时，是否会成为死信消息")
    void lengthLimit() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 初始化一个线程池
        /*ExecutorService executorService = new ThreadPoolExecutor(
                5,
                10,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(512),
                new ThreadPoolExecutor.AbortPolicy()
        );*/
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            AMQP.BasicProperties properties =
                    new AMQP.BasicProperties().builder()
                            .deliveryMode(2)
                            .build();
            for (int i = 0; i < 15; i++) {
                channel.basicPublish(DLDemo.ORIGINAL_EXCHANGE_NAME,
                        DLDemo.ORIGINAL_ROUTING_KEY,
                        properties,
                        ("发送多条消息 " + i).getBytes(StandardCharsets.UTF_8));
            }
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    LOGGER.info("死信队列收到消息 {}", message);
//                    countDownLatch.countDown();
                }
            };
            channel.basicConsume(DLDemo.DL_QUEUE_NAME, consumer);
            countDownLatch.await();
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
