package com.xiaobei.java.demo.java12;

import java.io.IOException;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/10/8 20:46
 */
public class FirstDemo {

    /**
     * 测试启用 shenandoah 收集器
     * TODO OracleJDK 不支持 Shenandoah 收集器
     * VM args: -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC
     * @param args
     */
    public static void main(String[] args) throws IOException {
        System.out.println("java 12");
        System.in.read();
    }

}
