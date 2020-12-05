package com.xiaobei.spring.web.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 07:26
 */
@EnableWebMvc
@ComponentScan(basePackages = "com.xiaobei.spring.web.demo.controller")
@Configuration
@PropertySource(value = "classpath:/application.properties")
public class SpringWebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * <a href="https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html">thymeleaf相关文档</a>
     * @return
     */
    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver(
            @Value("${spring.thymeleaf.prefix}") String prefix,
            @Value("${spring.thymeleaf.suffix}") String suffix,
            @Value("${spring.thymeleaf.cache}") Boolean cache,
            @Value("${spring.thymeleaf.encoding}") String encoding,
            @Value("${spring.thymeleaf.mode}") String mode) {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(suffix);
        templateResolver.setCacheable(cache);
        templateResolver.setCharacterEncoding(encoding);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine(
            SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine springTemplateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setContentType("text/html; charset=utf-8");
        viewResolver.setTemplateEngine(springTemplateEngine);
        return viewResolver;
    }

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
