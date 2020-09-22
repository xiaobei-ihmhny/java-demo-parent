package com.xiaobei.java.demo.i18n.spring.config.mvc;

import com.xiaobei.java.demo.i18n.spring.config.i18n.MessageSourceConfig;
import com.xiaobei.java.demo.i18n.spring.config.thymeleaf.ThymeleafConfig;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/10 7:44
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.xiaobei.java.demo.i18n.spring.controller")
@Import({MessageSourceConfig.class, ThymeleafConfig.class})
public class SpringWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public HttpMessageConverter<String> httpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }

    /**
     * 指定自定义的请求及响应编码配置
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(httpMessageConverter());
    }
}
