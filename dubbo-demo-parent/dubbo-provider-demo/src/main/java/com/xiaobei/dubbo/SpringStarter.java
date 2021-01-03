package com.xiaobei.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/02 21:32
 */
public class SpringStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringStarter.class);

    private static final Lock LOCK = new ReentrantLock();

    private static final Condition EXIT = LOCK.newCondition();

    /**
     * 启动之后可以看到相关的服务已经发布，启动后生成的地址信息如下：
     * <pre>{@code
     *     dubbo://192.168.163.1:20880/com.xiaobei.dubbo.api.IPayService?
     *     anyhost=true&application=dubbo-provider-demo&bean.name=com.xiaobei.dubbo.api.IPayService
     *     &bind.ip=192.168.163.1&bind.port=20880&deprecated=false&dubbo=2.0.2
     *     &dynamic=true&generic=false&interface=com.xiaobei.dubbo.api.IPayService&methods=pay&pid=17016
     *     &register=true&release=2.7.2&side=provider&timestamp=1609633772239
     * }</pre>
     *
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/dubbo/dubbo.xml");
        applicationContext.refresh();
        LOCK.lock();
        try {
            EXIT.await();
        } catch (InterruptedException e) {
            LOGGER.error("dubbo 服务被中断了...", e);
        } finally {
            LOCK.unlock();
        }

        applicationContext.close();
    }
}
