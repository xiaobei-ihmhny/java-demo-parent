package com.xiaobei.design.pattern.demo.factory.method.sample;

/**
 * 抽象的工厂接口
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 17:13:13
 */
public interface Creator {

    /**
     * 创建具体产品的方法，具体实现由子类完成
     * @return
     */
    Light createLight();
}
