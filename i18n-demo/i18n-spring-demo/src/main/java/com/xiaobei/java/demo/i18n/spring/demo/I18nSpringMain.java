package com.xiaobei.java.demo.i18n.spring.demo;

import com.xiaobei.java.demo.i18n.spring.config.i18n.MessageSourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-09 17:05:05
 */
public class I18nSpringMain {

    public static void main(String[] args) {
//        classPathXmlApplicationContext();
//        classPathXmlApplicationContext2();
//        annotationConfigApplicationContextProfile("test1");
//        annotationConfigApplicationContextProfile("test2");
        annotationConfigApplicationContextProfile("test3");
    }

    /**
     * 通过bean工厂获取想要的指定bean。
     */
    private static void classPathXmlApplicationContext2() {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("spring.xml");
        Object[] params = {"John", new Date()};
        MessageSource source = (MessageSource) context.getBean("messageSource2");
        String message = source.getMessage("greeting.common22", params, null);
        System.out.println(message);
    }


    /**
     * // TODO 此种方式要求配置的 MessageSource的bean的名称必须为 "messageSource"，why？
     */
    private static void classPathXmlApplicationContext() {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("spring.xml");
        Object[] params = {"John", new Date()};
        String message = context.getMessage("greeting.common22", params, null);
        System.out.println(message);
    }

    /**
     * 通过注解方式加载
     */
    private static void annotationConfigApplicationContextProfile(String profile) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles(profile);
        // 注解容器类
        context.register(MessageSourceConfig.class);
        context.refresh();
        Object[] params = {"John", new Date()};
        String message = context.getMessage("greeting.common22", params, null);
        System.out.println(message);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        message = context.getMessage("greeting.common22", params, null);
        System.out.println(message);

    }
}