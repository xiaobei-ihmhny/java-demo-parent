package com.xiaobei.java.demo.customize.autoconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-15 15:58:58
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.xiaobei.java.demo.customize.autoconfig.controller")
public class SpringWebMvcConfiguration {
}