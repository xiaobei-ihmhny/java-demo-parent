package com.xiaobei.design.pattern.demo.factory.method.sample;

/**
 * 抽象产品接口
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 17:08:08
 */
public interface Light {

    /**
     * 开灯
     */
    void turnOn();

    /**
     * 关灯
     */
    void turnOff();
}