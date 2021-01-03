package com.xiaobei.dubbo.consumer;

import com.xiaobei.dubbo.api.IPayService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/02 21:50
 */
public class ConsumerMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("classpath:/META-INF/dubbo/dubbo.xml");
        applicationContext.refresh();
        // 依赖查找
        IPayService payService = applicationContext.getBean(IPayService.class);
        // 进行远程调用
        String rpcResult = payService.pay("hello dubbo");
        System.out.println(rpcResult);
        applicationContext.close();
    }

}
