package com.xiaobei.rabbitmq.spring.xml;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * TODO
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-17 06:56:56
 */
public class SpringRabbitmqWithXml {

    public static void main(String[] args) {
        GenericXmlApplicationContext applicationContext =
                new GenericXmlApplicationContext("classpath:META-INF/spring-rabbitmq.xml");
        AmqpTemplate amqpTemplate = applicationContext.getBean(AmqpTemplate.class);
        amqpTemplate.convertAndSend("myqueue", "foo");
        String foo = (String) amqpTemplate.receiveAndConvert("myqueue");
        System.out.println(foo);
        applicationContext.close();
    }
}
