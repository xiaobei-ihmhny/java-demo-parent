package com.xiaobei.java.demo.i18n.spring.config.mvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/10 7:44
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.xiaobei.java.demo.i18n.spring.controller")
public class SpringWebMvcConfig {
}
