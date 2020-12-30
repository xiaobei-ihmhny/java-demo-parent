package com.xiaobei.java.demo.junit5;

import org.junit.jupiter.api.*;

/**
 * 为了解决测试类数量爆炸的问题，JUnit5 提供了
 * {@link org.junit.jupiter.api.Nested @Nested} 注解，
 * 能够以静态内部成员类的形式对测试用例类进行逻辑分组。
 * 每个内部类都可以有自已的生命周期方法，这些方法将按照从外到内的层次执行。
 * 嵌套类同样可以使用 {@link DisplayName @DisplayName} 表示。
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-30 10:15:15
 */
@DisplayName("内嵌测试类")
public class NestUnitTest {

    @BeforeEach
    void init() {
        System.out.println("测试方法执行前准备");
    }

    @Nested
    @DisplayName("第一个嵌套测试类")
    class FirstNestClass {

        @AfterEach
        void destroy() {
            System.out.println("第一个嵌套测试类当前测试方法结束");
        }

        @Test
        void test() {
            System.out.println("第一个嵌套测试类执行测试");
        }
    }

    @Nested
    @DisplayName("第二个嵌套测试类")
    class SecondNestClass {

        @BeforeEach
        void init() {
            System.out.println("第二个嵌套测试类当前测试方法开始");
        }

        @AfterEach
        void destroy() {
            System.out.println("第二个嵌套测试类当前测试方法结束");
        }

        @Test
        void test() {
            System.out.println("第二个嵌套测试类执行测试");
        }
    }

}