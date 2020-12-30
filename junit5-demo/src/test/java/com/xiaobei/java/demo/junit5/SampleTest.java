package com.xiaobei.java.demo.junit5;

import org.junit.jupiter.api.*;

/**
 * {@link DisplayName} 可在类或方法上，
 * 用于显示测试项名称，该注解的属性值会在测试报告中展示对应的名称，
 * 用在类上表示该类在测试报告中的名称，在方法上表示该方法在测试报告中的名称
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-30 09:59:59
 */
@DisplayName("简单测试")
public class SampleTest {

    /**
     * {@link BeforeAll} 定义整个测试类在开始前的操作，只能修饰 {@code static} 方法
     * 用于在测试过程中所需要的全局数据和外部资源的初始化。
     */
    @BeforeAll
    public static void init() {
        System.out.println("初始化数据");
    }

    /**
     * {@link AfterAll} 定义整个测试类在结束后的操作，只能修饰 {@code static} 方法
     * 用于在测试过程中所需要的全局数据和外部资源的清理。
     */
    @AfterAll
    public static void cleanup() {
        System.out.println("清理数据");
    }

    /**
     * {@link BeforeEach} 在每个测试用例方法开始前执行，
     * 主要负责该测试用例所需要的运行环境的准备。
     */
    @BeforeEach
    public void tearUp() {
        System.out.println("当前测试方法开始");
    }

    /**
     * {@link AfterEach} 在每个测试用例方法结束后执行，
     * 主要负责该测试用例所需要的运行环境的销毁。
     */
    @AfterEach
    public void tearDown() {
        System.out.println("当前测试方法结束");
    }

    @DisplayName("第一个测试")
    @Test
    void testFirstTest() {
        System.out.println("我的第一个测试");
    }

    @DisplayName("第二个测试")
    @Test
    void testSecondTest() {
        System.out.println("我的第二个测试");
    }

    /**
     * {@link Disabled} 用来禁用特定的测试类或测试方法，
     * 该注解标记的方法不会执行，只有单独的方法信息打印
     */
    @DisplayName("第三个测试")
    @Disabled
    @Test
    void testThirdTest() {
        System.out.println("我的第三个测试");
    }

    /**
     * {@link RepeatedTest} 用于对测试方法设置运行次数，
     * 允许让测试方法进行重复运行。
     */
    @DisplayName("重复测试")
    @RepeatedTest(value = 3)
    void repeatedTest() {
        System.out.println("执行测试内容");
    }

    /**
     * {@link RepeatedTest#name() RepeatedTest.name} 中可以使用以下参数：
     * {currentRepetition} 变量表示已经重复的次数，
     * {totalRepetitions} 变量表示总共要重复的次数，
     * {displayName} 变量表示测试方法显示名称，
     * 我们直接就可以使用这些内置的变量来重新定义测试方法重复运行时的名称。
     */
    @DisplayName("重复测试")
    @RepeatedTest(value = 3, name = "{displayName} 第 {currentRepetition} 次， 共 {totalRepetitions} 次")
    void repeatedCustomNameTest() {
        System.out.println("执行测试内容");
    }

}