package com.xiaobei.general.spring.demo;

import com.xiaobei.general.spring.demo.domain.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 10:34
 */
public class ApplicationContextMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("META-INF/application.xml");
        User user = applicationContext.getBean(User.class);
        System.out.println(user);
    }
}
