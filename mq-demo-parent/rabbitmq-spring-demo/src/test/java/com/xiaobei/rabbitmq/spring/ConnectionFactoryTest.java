package com.xiaobei.rabbitmq.spring;

import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * spring-rabbit 2.3.2 连接工厂示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-18 21:55:55
 */
@DisplayName("spring-rabbit 2.3.2 连接工厂示例")
@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class ConnectionFactoryTest {

    @Configuration
    @PropertySource(value = "classpath:META-INF/rabbitmq.properties")
    static class ConnectionFactoryConfig {
        @Value("${rabbitmq.host}") private String host;
        @Value("${rabbitmq.port}") private Integer port;
        @Value("${rabbitmq.vhost}") private String vhost;
        @Value("${rabbitmq.username}") private String username;
        @Value("${rabbitmq.password}") private String password;

        /**
         * 配置 {@link PooledChannelConnectionFactory} 示例
         * @return
         */
        @Bean
        public PooledChannelConnectionFactory pdf() {
            ConnectionFactory rabbitConnectionFactory = new ConnectionFactory();
            rabbitConnectionFactory.setHost(host);
            PooledChannelConnectionFactory pcf =
                    new PooledChannelConnectionFactory(rabbitConnectionFactory);
            pcf.setPoolConfigurer((pool, isTx) -> {
                if(isTx) {
                    // TODO configure the transactional pool
                } else {
                    // TODO configure the non-transactional pool
                }
            });
            return pcf;
        }

    }

}
