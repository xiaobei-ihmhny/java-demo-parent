package com.xiaobei.design.pattern.demo.factory.method;

import com.xiaobei.design.pattern.demo.factory.method.sample.BlueCreator;
import com.xiaobei.design.pattern.demo.factory.method.sample.Creator;
import com.xiaobei.design.pattern.demo.factory.method.sample.Light;
import com.xiaobei.design.pattern.demo.factory.method.sample.RedCreator;
import org.junit.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 17:17:17
 */
public class FactoryMethodTest {

    /**
     * BlueLight On
     * BlueLight Off
     * RedLight On
     * RedLight Off
     */
    @Test
    public void sample() {
        Creator creator = new BlueCreator();
        Light light = creator.createLight();
        light.turnOn();
        light.turnOff();

        creator = new RedCreator();
        light = creator.createLight();
        light.turnOn();
        light.turnOff();
    }
}