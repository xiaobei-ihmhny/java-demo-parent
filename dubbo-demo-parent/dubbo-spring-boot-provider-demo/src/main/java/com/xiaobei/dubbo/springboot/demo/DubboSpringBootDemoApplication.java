package com.xiaobei.dubbo.springboot.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
public class DubboSpringBootDemoApplication implements ApplicationContextAware {


	public static void main(String[] args) {
		SpringApplication.run(DubboSpringBootDemoApplication.class, args);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Object obj1 = applicationContext.getBean("payServiceImpl");
		Object obj2 = applicationContext.getBean("ServiceBean:com.xiaobei.dubbo.api.IPayService");
		System.out.println(111);
	}
}
