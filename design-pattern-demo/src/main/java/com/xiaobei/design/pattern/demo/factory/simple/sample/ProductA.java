package com.xiaobei.design.pattern.demo.factory.simple.sample;

/**
 * 具体的产品A
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 14:51:51
 */
public class ProductA implements IProduct {

    @Override
    public void method() {
        System.out.println("生产A产品");
    }
}