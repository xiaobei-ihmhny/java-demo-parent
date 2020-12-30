package com.xiaobei.java.demo.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * JUnit5 参数化测试
 * 除了依赖模块 {@code junit-jupiter-engine} 外
 * 还需要再添加模块 {@code junit-jupiter-params}。
 * <pre>{@code
 *   <dependency>
 *      <groupId>org.junit.jupiter</groupId>
 *      <artifactId>junit-jupiter-params</artifactId>
 *      <version>5.7.0</version>
 *      <scope>test</scope>
 *   </dependency>
 * }</pre>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-30 10:55:55
 */
@DisplayName("JUnit5 参数化测试模块")
public class ParamsTest {

    /**
     * {@link ValueSource} 提供了最简单的数据参数源，支持 Java 的八大基本类型、字符串和 Class，
     * 使用时时赋值给对应的类型属性，以数组方式传递。
     * @param num int数组
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 8})
    void numberShouldBeEven(int num) {
        Assertions.assertEquals(0, num % 2);
    }

    /**
     * {@link ValueSource} 提供了最简单的数据参数源，支持 Java 的八大基本类型、字符串和 Class，
     * 使用时时赋值给对应的类型属性，以数组方式传递。
     * @param name String数组
     */
    @ParameterizedTest
    @ValueSource(strings = {"xiaobei", "huihui", "natie"})
    void printTitle(String name) {
        System.out.println(name);
    }

    /**
     * {@link CsvSource} 测试 csv 格式的数据
     * {@link CsvSource#delimiter()} 可以自定义符号，默认情况下为 “,”
     * @param id
     * @param name
     */
    @ParameterizedTest
    @CsvSource(value = {"1,huihui", "2,xiaobei", "3,tietie"})
    void dataFromCsv(long id, String name) {
        System.out.printf("id: %d, name: %s", id, name);
    }

    /**
     * {@link CsvFileSource} 读取外部 CSV 格式文件数据的方式作为数据源的实现。
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/csvFile.csv", delimiter = '\t')
    void dataFromCsvFile(long index, String name) {
        System.out.printf("index: %d, name: %s", index, name);
    }
}