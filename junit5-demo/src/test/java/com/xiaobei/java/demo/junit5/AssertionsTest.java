package com.xiaobei.java.demo.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * {@link org.junit.jupiter.api.Assertions} 是 JUnit5 提供的全新的断言 API，
 * 支持 Lambda 表达式，可以接收 Lambda 表达式参数，
 * 在断言消息使用 Lambda 表达式的一个优点就是它是延迟计算的，
 * 如果消息构造开销很大，这样做一定程度上可以节省时间和资源。
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-30 10:42:42
 */
@DisplayName("全新 Assertions 断言测试示例")
public class AssertionsTest {

    /**
     * 如果分组断言中任一个断言的失败，
     * 都会将以 {@link org.opentest4j.MultipleFailuresError} 错误进行抛出提示。
     */
    @Test
    void groupAssertions() {
        int[] numbers = {0, 1, 2, 3, 4};
        Assertions.assertAll("numbers",
                () -> Assertions.assertEquals(numbers[1], 1),
                () -> Assertions.assertEquals(numbers[2], 2),
                () -> Assertions.assertEquals(numbers[4], 4));
    }

    /**
     * 超时操作的测试
     *
     * {@link Assertions#assertTimeoutPreemptively(Duration, Executable)}
     * 用于测试指定时间内是否可以完成指定的测试
     */
    @Test
    @DisplayName("超时方法测试")
    void completeInAssignTime() {
        Assertions.assertTimeoutPreemptively(
                Duration.of(1, ChronoUnit.SECONDS),
                () -> Thread.sleep(2000));
    }

    /**
     * {@link Assertions#assertThrows(Class, Executable)}
     * 第一个参数异常类型，第二个参数为函数式接口参数，
     * 不需要参数，也没有返回，支持使用 Lambda 表达式
     */
    @Test
    @DisplayName("测试捕获的异常")
    void assertThrowsException() {
        String str = null;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Integer.valueOf(str));

    }


}