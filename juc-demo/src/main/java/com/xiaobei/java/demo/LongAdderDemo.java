package com.xiaobei.java.demo;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-30 08:45:45
 */
public class LongAdderDemo {

    public static void main(String[] args) {
        LongAdder la = new LongAdder();
        la.add(1);
    }
}