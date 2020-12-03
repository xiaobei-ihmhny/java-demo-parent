package com.xiaobei.design.pattern.demo.factory.simple.sample;

/**
 * 具体的产品B
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 14:53:53
 */
public class ProductB implements IProduct {

    @Override
    public void method() {
        System.out.println("这是B产品");
    }
}