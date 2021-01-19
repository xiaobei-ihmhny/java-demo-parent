package com.xiaobei.rabbitmq.spring.javaconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * java configuration 方式配置 spring amqp
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 06:59:59
 */
@Configuration
public class AmqpConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpConfig.class);

    @Bean
    public PropertySourcesPlaceholderConfigurer configurer(
            @Value("classpath:META-INF/rabbitmq.properties") Resource resource) {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setFileEncoding("UTF-8");
        configurer.setLocation(resource);
        return configurer;
    }

    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory(@Value("${rabbitmq.host}") String host,
                                               @Value("${rabbitmq.port}") Integer port,
                                               @Value("${rabbitmq.vhost}") String vhost,
                                               @Value("${rabbitmq.username}") String username,
                                               @Value("${rabbitmq.password}") String password) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        /*
         * 设置连接工厂创建的 channel 数量，默认情况下为 25 个
         * 默认大小设置的位置为
         * {@link org.springframework.amqp.rabbit.connection.CachingConnectionFactory.DEFAULT_CHANNEL_CACHE_SIZE}
         */
        cachingConnectionFactory.setChannelCacheSize(25);
        /*
         * 默认值为 0
         * {@link org.springframework.amqp.rabbit.connection.CachingConnectionFactory.channelCheckoutTimeout}
         * 当设置为非0的正整数时，表示指定的毫秒时间内，获取不到 channel 时，会抛出异常
         * {@link org.springframework.amqp.AmqpException}
         */
        cachingConnectionFactory.setChannelCheckoutTimeout(0);
        /*
         * 默认的 cacheMode 为 CacheMode.CHANNEL
         */
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        /*
         * 当运行于一个集群环境时，可以使用如下配置同时配置多个连接信息
         * {@link org.springframework.amqp.rabbit.connection.AbstractConnectionFactory.setAddresses}
         * 格式如下：host[:port],...
         */
//        cachingConnectionFactory.setAddresses("");
        /*
         * 默认为 AbstractConnectionFactory.AddressShuffleMode.NONE
         * 参见：https://docs.spring.io/spring-amqp/docs/current/reference/html/#cluster
         */
        cachingConnectionFactory.setAddressShuffleMode(AbstractConnectionFactory.AddressShuffleMode.NONE);
        /*
         * 配置创建连接的线程工厂，
         * 使用 spring-context 中的 CustomizableThreadFactory 设置线程前缀为 "rabbitmq-"
         */
        cachingConnectionFactory.setConnectionThreadFactory(
                new CustomizableThreadFactory("rabbitmq-"));
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setVirtualHost(vhost);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean(name = "rabbitmqAdmin")
    public AmqpAdmin rabbitmqAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        /* 配置重连策略 start */
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitAdmin.setRetryTemplate(retryTemplate);
        /* 配置重连策略 end */
        return rabbitAdmin;
    }

    @Bean(name = "rabbitmqTemplate")
    public AmqpTemplate rabbitmqTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        /* 确认消息配置 start */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack) {
                    LOGGER.info("消息确认成功");
                } else {
                    // nack
                    LOGGER.info("消息确认失败");
                }
            }
        });
        /* 确认消息配置 end */
        /* 回发消息配置 start */
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                // TODO
            }
        });
        /* 回发消息配置 end */
        return rabbitTemplate;
    }

    @Bean(name = "my-queue")
    public Queue queue() {
        return new Queue("my-queue");
    }
}
