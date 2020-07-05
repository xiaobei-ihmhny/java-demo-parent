package com.xiaobei.java.demo;

import java.io.IOException;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/5 7:52
 */
public class A {

    /**
     * 在 toString方法中执行其他恶意代码
     * @return
     */
    @Override
    public String toString() {
        try {
            Runtime.getRuntime().exec("notepad.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.toString();
    }
}
