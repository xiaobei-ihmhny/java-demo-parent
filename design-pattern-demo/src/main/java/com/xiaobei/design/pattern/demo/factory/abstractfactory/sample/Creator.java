package com.xiaobei.design.pattern.demo.factory.abstractfactory.sample;

/**
 * 工厂接口
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/03 20:55
 */
public interface Creator {

    ProductA createProductA();

    ProductB createProductB();
}
