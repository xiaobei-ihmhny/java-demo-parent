package com.xiaobei.java.demo.i18n.spring.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/9 23:01
 */
@Configuration
public class MessageSourceConfig {

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
    @Bean(name = "messageSource")
    @Profile("test1")
    public MessageSource classPathMessageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        // 如果需要指定类路径，basename的开头需要是"classpath:"
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    /**
     * TODO 目前的刷新功能测试稍有问题
     * https://www.jianshu.com/p/52712dedb0bd
     * @return
     */
    @Bean(name = "messageSource")
    @Profile("test2")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        // 如果需要指定类路径，basename的开头需要是"classpath:"
        messageSource.setBasenames("file:E:\\project\\i18n\\messages\\messages_zh_CN.properties",
                "file:E:\\project\\i18n\\messages\\messages_en_US.properties");
        // 设置缓存时间
        messageSource.setCacheSeconds(1);
        return messageSource;
    }
}
