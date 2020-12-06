package com.xiaobei.spring.web.demo.webtestclient;

import com.xiaobei.spring.web.demo.controller.DemoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.function.RouterFunction;

/**
 * TODO 参见：https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#webtestclient
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 23:25
 */

public class WebTestClientTest {

    /**
     * 参见：https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#webtestclient-fn-config
     */
    static class bindToRouterFunction {
//        RouterFunction<?> route = ...
//        client = WebTestClient.bindToRouterFunction(route).build();
    }

    /**
     * 参见：https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#webtestclient-server-config
     */
    public void bindToServer() {
        WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

    /**
     * 参见：https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#webtestclient-client-config
     */
    public void clientConfig() {
        WebTestClient client = WebTestClient
                .bindToController(new DemoController())
                .configureClient()
                .baseUrl("/test")
                .build();
    }


    @ExtendWith(SpringExtension.class)
    @WebAppConfiguration(value = "classpath:templates")
    @ContextHierarchy({
            @ContextConfiguration(classes = WebMvcBindApplicationContext.RootConfig.class),
            @ContextConfiguration(classes = WebMvcBindApplicationContext.WebConfig.class)
    })
    static class WebMvcBindApplicationContext {

        @Configuration
        static class RootConfig {

        }

        @Configuration
        static class WebConfig {

        }

        @Autowired
        WebApplicationContext wac;

        WebTestClient client;

        @BeforeEach
        void setUp() {
            client = MockMvcWebTestClient.bindToApplicationContext(this.wac).build();
        }

    }



    public void bindToController() {
        // webflux
        WebTestClient webfluxClient = WebTestClient.bindToController(new DemoController()).build();
        // webmvc
        WebTestClient webmvcClient = MockMvcWebTestClient.bindToController(new DemoController()).build();
    }


    /**
     * webflux 绑定 {@link ApplicationContext}
     * @since JUnit Jupiter
     */
    @SpringJUnitConfig(WebFluxBindApplicationContext.WebConfig.class)
    static class WebFluxBindApplicationContext {

        /**
         * TODO 指定相关配置信息
         */
        @Configuration
        static class WebConfig {

        }

        WebTestClient client;

        @BeforeEach
        void setUp(ApplicationContext context) {// 注入 ApplicationContext
            client = WebTestClient.bindToApplicationContext(context).build();// 绑定到 WebTestClient
        }

    }



}
