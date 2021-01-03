package com.xiaobei.dubbo.springboot.demo;

import com.xiaobei.dubbo.api.IPayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DubboSpringBootProviderDemoApplicationTests {

	@Autowired
	private IPayService payService;

	@Test
	void contextLoads() {
		payService.pay("1111");
	}

}
