package com.xiaobei.design.pattern.demo.factory.method.sample;

/**
 * 具体的产品（红灯）
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 17:09:09
 */
public class RedLight implements Light {

    @Override
    public void turnOn() {
        System.out.println("RedLight On");
    }

    @Override
    public void turnOff() {
        System.out.println("RedLight Off");
    }
}