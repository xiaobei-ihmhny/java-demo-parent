package com.xiaobei.java.demo;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-16 22:40:40
 */
@EnableWebMvc
@EnableCaching
@Configuration
@ComponentScan(basePackageClasses = SpringMvcConfiguration.class)
public class SpringMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * TODO String类型的 @ResponseBody 请求乱码未解决
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决controller返回字符串中文乱码
        for (int i = 0; i < converters.size(); i++) {
            HttpMessageConverter<?> converter = converters.get(i);
            if (converter instanceof StringHttpMessageConverter) {
                converters.remove(i);
                HttpMessageConverter<?> stringConverter
                        = new StringHttpMessageConverter(StandardCharsets.UTF_8);
                converters.add(stringConverter);
                break;
            }
        }
    }
}