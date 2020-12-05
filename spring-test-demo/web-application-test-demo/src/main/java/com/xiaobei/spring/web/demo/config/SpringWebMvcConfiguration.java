package com.xiaobei.spring.web.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 07:26
 */
@EnableWebMvc
@ComponentScan(basePackages = "com.xiaobei.spring.web.demo.controller")
@Configuration
public class SpringWebMvcConfiguration implements WebMvcConfigurer {



}
