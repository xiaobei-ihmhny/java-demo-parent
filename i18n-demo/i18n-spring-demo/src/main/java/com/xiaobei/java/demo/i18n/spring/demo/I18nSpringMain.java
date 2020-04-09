package com.xiaobei.java.demo.i18n.spring.demo;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-09 17:05:05
 */
public class I18nSpringMain {

    public static void main(String[] args) {
        classPathXmlApplicationContext();
    }

    private static void classPathXmlApplicationContext() {
        MessageSource context
                = new ClassPathXmlApplicationContext("spring.xml");
        Object[] params = {"John", new Date()};
        String message = context.getMessage("greeting.common2", params, null);
        System.out.println(message);
    }
}