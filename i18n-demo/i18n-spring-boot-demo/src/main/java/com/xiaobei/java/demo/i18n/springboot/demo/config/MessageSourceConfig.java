package com.xiaobei.java.demo.i18n.springboot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/7 6:48
 */
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {

    /**
     * 使用会话区域解析器 {@link org.springframework.web.servlet.i18n.SessionLocaleResolver}
     * 代替默认的 {@link org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver}
     * @return
     */
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//        // 设置默认的区域
//        localeResolver.setDefaultLocale(Locale.CHINA);
//        return localeResolver;
//    }

    /**
     * 使用会话区域解析器 {@link org.springframework.web.servlet.i18n.SessionLocaleResolver}
     * 代替默认的 {@link org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver}
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        // 设置默认的区域
        localeResolver.setDefaultLocale(Locale.CHINA);
        // 设置cookie有效期
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }

    /**
     * 使用参数修改用户的区域
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 设置请求地址的参数 默认为：locale
        lci.setParamName(LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
