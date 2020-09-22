package com.xiaobei.java.demo.i18n.spring.demo;

import com.xiaobei.java.demo.i18n.spring.config.i18n.MessageSourceConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-09 17:05:05
 */
public class I18nSpringMain {

    /**
     * 通过bean工厂获取想要的指定bean。
     */
    @Test
    public void classPathXmlApplicationContext2() {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("spring.xml");
        Object[] params = {"John", new Date()};
        MessageSource source = (MessageSource) context.getBean("messageSource");
        String message = source.getMessage("greeting.common22", params, Locale.US);
        System.out.println(message);
    }


    /**
     * 此种方式要求配置的 MessageSource的bean的名称必须为 "messageSource"
     * @see AbstractApplicationContext#initMessageSource()
     */
    @Test
    public void classPathXmlApplicationContext() {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("spring.xml");
        Object[] params = {"John", new Date()};
        String message = context.getMessage("greeting.common22", params, null);
        System.out.println(message);
    }

    /**
     * 通过注解方式加载
     */
    @Test
    public void annotationConfigApplicationContextIncludeProfile() {
//        annotationConfigApplicationContextProfile("test1");
        annotationConfigApplicationContextProfile("test2");
    }

    /**
     * 加载指定的 profile
     * @param profile
     */
    private void annotationConfigApplicationContextProfile(String profile) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(profile);
        // 注解 Configuration Class
        context.register(MessageSourceConfig.class);
        context.refresh();
        Object[] params = {"John", new Date()};
        String message = context.getMessage("greeting.common22", params, null);
        System.out.println(message);
        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        message = context.getMessage("greeting.common22", params, null);
        System.out.println(message);
    }
}