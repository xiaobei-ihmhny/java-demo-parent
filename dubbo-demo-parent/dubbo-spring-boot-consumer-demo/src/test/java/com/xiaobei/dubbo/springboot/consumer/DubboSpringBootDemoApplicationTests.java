package com.xiaobei.dubbo.springboot.consumer;

import com.xiaobei.dubbo.api.IPayService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DubboSpringBootDemoApplicationTests {

	@Reference
	private IPayService payService;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void consumerDubbo() {
		String result = payService.pay("测试spring-boot方法调用dubbo服务");
		System.out.println(result);
	}

}
