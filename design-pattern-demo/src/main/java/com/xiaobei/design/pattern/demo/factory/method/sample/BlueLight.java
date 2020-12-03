package com.xiaobei.design.pattern.demo.factory.method.sample;

/**
 * 具体的产品（蓝灯）
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 17:11:11
 */
public class BlueLight implements Light {

    @Override
    public void turnOn() {
        System.out.println("BlueLight On");
    }

    @Override
    public void turnOff() {
        System.out.println("BlueLight Off");
    }
}