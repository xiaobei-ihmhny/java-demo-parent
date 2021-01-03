package com.xiaobei.dubbo.provider;

import org.apache.dubbo.container.Main;
import org.apache.dubbo.container.spring.SpringContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/02 23:25
 */
@SuppressWarnings("SpellCheckingInspection")
@DisplayName("Dubbo 初体验")
public class DubboStarterTest {

    /**
     * 各启动器的公共逻辑
     */
    static abstract class AbstractStarter {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        /**
         * 使用启动 spring 的方式启动 dubbo 服务
         * @param configLocation
         */
        protected void springStarter(String configLocation) {
            Lock lock = new ReentrantLock();
            Condition stop = lock.newCondition();
            ClassPathXmlApplicationContext applicationContext =
                    new ClassPathXmlApplicationContext(configLocation);
            // 启动 spring 应用上下文
            applicationContext.refresh();
            lock.lock();
            try {
                stop.await();
            } catch (InterruptedException e) {
                logger.warn("dubbo应用被中断了...", e);
            } finally {
                lock.unlock();
                // 关闭 spring 应用上下文
                applicationContext.close();
            }
        }
    }

    /**
     * 示例无注册中心的服务注册
     */
    @Nested
    @DisplayName("无注册中心的启动器")
    class NoRegisterStarter extends AbstractStarter {

        private final Logger logger = LoggerFactory.getLogger(NoRegisterStarter.class);

        /**
         * 配置当前测试类的系统变量 {@code dubbo.spring.config}
         * 的值为 {@code META-INF/dubbo/dubbo.xml}
         */
        @BeforeEach
        void initSystemProperty() {
            System.setProperty(SpringContainer.SPRING_CONFIG, "META-INF/dubbo/dubbo.xml");
        }

        /**
         * TODO dubbo 中的其他容器如何加载？
         */
        @Test
        @DisplayName("使用dubbo提供的 Main 启动应用")
        void dubboMainStarter() {
            Main.main(new String[]{"spring"});
        }

        /**
         * 使用 Spring的方式启动应用
         */
        @DisplayName("使用spring启动应用")
        @ParameterizedTest
        @ValueSource(strings = "classpath:/META-INF/dubbo/dubbo.xml")
        void dubboSpringStarter(String configLocation) {
            springStarter(configLocation);
        }
    }

    @Nested
    @DisplayName("zookeeper 作为注册中心示例")
    class ZookeeperRegisterStarter extends AbstractStarter {

        @BeforeEach
        void initSystemProperty() {
            System.setProperty(SpringContainer.SPRING_CONFIG,"META-INF/dubbo/dubbo-zookeeper.xml");
        }

        @Test
        @DisplayName("使用dubbo提供的 Main 启动应用")
        void dubboMainStarter() {
            Main.main(new String[]{"spring"});
        }

        /**
         * 使用 Spring的方式启动应用
         */
        @DisplayName("使用spring启动应用")
        @ParameterizedTest
        @ValueSource(strings = "classpath:/META-INF/dubbo/dubbo-zookeeper.xml")
        void dubboSpringStarter(String configLocation) {
            springStarter(configLocation);
        }

    }


}
