package com.xiaobei.design.pattern.demo.factory.abstractfactory;

import com.xiaobei.design.pattern.demo.factory.abstractfactory.sample.*;
import org.junit.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/03 20:59
 */
public class AbstractFactoryTest {

    /**
     * 产品A系列中1型号产品的方法
     * 产品B系列中1型号产品的方法
     * 产品A系列中2型号产品的方法
     * 产品B系列中2型号产品的方法
     */
    @Test
    public void sample() {
        Creator creator = new ConcreteCreator1();
        ProductA productA = creator.createProductA();
        ProductB productB = creator.createProductB();
        productA.methodA();
        productB.methodB();

        creator = new ConcreteCreator2();
        productA = creator.createProductA();
        productB = creator.createProductB();
        productA.methodA();
        productB.methodB();
    }
}
