package com.xiaobei.general.spring.demo.config;

import com.xiaobei.general.spring.demo.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 10:50
 */
@Configuration
public class SpringConfig {

    @Bean(name = "natie")
    public User user() {
        User user = new User();
        user.setUsername("natie");
        user.setAge(1);
        return user;
    }
}
