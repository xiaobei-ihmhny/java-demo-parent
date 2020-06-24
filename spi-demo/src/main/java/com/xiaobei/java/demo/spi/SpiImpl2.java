package com.xiaobei.java.demo.spi;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-06-24 13:46:46
 */
public class SpiImpl2 implements SPIService {
    @Override
    public void execute() {
        System.out.println("spiImpl2.execute()");
    }
}