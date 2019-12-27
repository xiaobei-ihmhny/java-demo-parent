package com.xiaobei.java.demo;

import java.math.BigDecimal;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-25 10:05:05
 */
public class Demo {

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "price=" + price +
                '}';
    }
}