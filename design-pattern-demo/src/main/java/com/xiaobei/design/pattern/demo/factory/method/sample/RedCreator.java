package com.xiaobei.design.pattern.demo.factory.method.sample;

/**
 * 生产具体产品 {@link RedLight} 的具体工厂
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 17:14:14
 */
public class RedCreator implements Creator {

    @Override
    public Light createLight() {
        return new RedLight();
    }
}