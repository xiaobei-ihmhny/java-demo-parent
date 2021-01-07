package com.xiaobei.spi.demo.java.spi;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-06-24 13:46:46
 */
public class SpiImpl1 implements SPIService {
    @Override
    public void execute() {
        System.out.println("spiImpl1.execute()");
    }
}