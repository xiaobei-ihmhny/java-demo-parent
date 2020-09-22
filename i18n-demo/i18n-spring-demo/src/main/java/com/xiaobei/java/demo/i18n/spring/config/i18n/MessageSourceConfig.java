package com.xiaobei.java.demo.i18n.spring.config.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * {@link ReloadableResourceBundleMessageSource} 会根据缓存时间是否
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/9 23:01
 */
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {

    /**
     * ReloadableResourceBundleMessageSource的优点
     * 在于可以不重启进程动态刷新配置，以及指定资源目录，不需要强制放在classpath下面，
     * 如果需要放在classpath中，那么只需要在basename中的资源路径上添加"classpath:"，
     * 便可以在classpath中查找配置。
     *
     * messageSource.setCacheSeconds用于设置配置的过期时间，单位为秒，
     * messageSource.setCacheSeconds(5)代表每5秒配置文件就会过期，再重新查询时，就会重新加载配置。
     *
     * @return
     */
    @Profile("test1")
    @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource classPathMessageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        // 如果需要指定类路径，basename的开头需要是"classpath:"
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setCacheSeconds(5);
        // 设置编码
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * https://www.jianshu.com/p/52712dedb0bd
     * https://blog.csdn.net/djrm11/article/details/96638337
     * @return
     */
    @Profile("test2")
    @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        // url协议方式 文件地址
        messageSource.setBasename("file:/D:/project/i18n/messages");
        // 设置缓存时间
        messageSource.setCacheSeconds(8);
        // 设置编码
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * 使用网络地址
     * @return
     */
    @Profile("test3")
    @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource urlMessageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        // url协议方式
        messageSource.setBasename("https://oss-test1.s3.cn-north-1.jdcloud-oss.com/i18n/messages");
        // 设置缓存时间
        messageSource.setCacheSeconds(1);
        // 设置编码
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * {@link ResourceBundleMessageSource} 使用示例
     * 注意：其只能支持 {@code classpath} 形式的 {@code basename}
     * @return
     */
    @Profile("test4")
    @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource resourceBundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 只支持 classpath 形式的 basename
        messageSource.setBasename("i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


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
     * 使用参数修改用户的区域，这种方式需要改变页面url地址，不太好
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
