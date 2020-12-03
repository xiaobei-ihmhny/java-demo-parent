package com.xiaobei.design.pattern.demo.factory.simple.sample;

import org.springframework.util.StringUtils;

/**
 * 工厂类 {@link Creator}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 14:54:54
 */
public class Creator {

    private Creator(){}

    public static IProduct createProduct(String productName) {
        if(StringUtils.isEmpty(productName)) {
            return null;
        }
        if("A".equals(productName)) {
            return new ProductA();
        } else if("B".equals(productName)) {
            return new ProductB();
        } else {
            return null;
        }
    }
}