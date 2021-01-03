package com.xiaobei.dubbo.consumer;

import com.xiaobei.dubbo.api.IPayService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/03 10:10
 */
@DisplayName("Dubbo 消费端测试示例")
public class DubboConsumerTest {

    private void refreshApplication(String configLocation) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(configLocation);
        applicationContext.refresh();
        IPayService payService = applicationContext.getBean(IPayService.class);
        System.out.println(payService.pay("hello dubbo"));
        applicationContext.close();
    }

    @DisplayName("不存在注册中心的调用示例")
    @ParameterizedTest
    @ValueSource(strings = "classpath:/META-INF/dubbo/dubbo.xml")
    void noRegister(String configLocation) {
        refreshApplication(configLocation);
    }

    @DisplayName("使用zookeeper作为注册中心示例")
    @ParameterizedTest
    @ValueSource(strings = "classpath:/META-INF/dubbo/dubbo-zookeeper.xml")
    void zookeeperRegister(String configLocation) {
        refreshApplication(configLocation);
    }
}
