package com.xiaobei.design.pattern.demo.factory.abstractfactory.sample;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/03 20:58
 */
public class ConcreteCreator2 implements Creator {

    @Override
    public ProductA createProductA() {
        return new ProductA2();
    }

    @Override
    public ProductB createProductB() {
        return new ProductB2();
    }
}
