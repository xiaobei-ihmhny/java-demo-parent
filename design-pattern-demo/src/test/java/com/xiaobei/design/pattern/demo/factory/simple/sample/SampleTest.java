package com.xiaobei.design.pattern.demo.factory.simple.sample;

import org.junit.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 14:58:58
 */
public class SampleTest {

    /**
     * 测试基础的“简单工厂模式”
     */
    @Test
    public void test() {
        IProduct productA = Creator.createProduct("A");
        productA.method();

        IProduct productB = Creator.createProduct("B");
        productB.method();
    }
}