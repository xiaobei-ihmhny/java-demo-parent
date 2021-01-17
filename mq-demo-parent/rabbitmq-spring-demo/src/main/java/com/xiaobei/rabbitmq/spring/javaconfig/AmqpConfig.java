package com.xiaobei.rabbitmq.spring.javaconfig;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

/**
 * java configuration 方式配置 spring amqp
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 06:59:59
 */
@Configuration
public class AmqpConfig {

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
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setVirtualHost(vhost);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean(name = "rabbitmqAdmin")
    public AmqpAdmin rabbitmqAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean(name = "rabbitmqTemplate")
    public AmqpTemplate rabbitmqTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean(name = "my-queue")
    public Queue queue() {
        return new Queue("my-queue");
    }
}
