package com.xiaobei.design.pattern.demo.factory.abstractfactory.sample;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/03 20:57
 */
public class ConcreteCreator1 implements Creator {

    @Override
    public ProductA createProductA() {
        return new ProductA1();
    }

    @Override
    public ProductB createProductB() {
        return new ProductB1();
    }
}
