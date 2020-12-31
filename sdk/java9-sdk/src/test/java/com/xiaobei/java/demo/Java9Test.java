package com.xiaobei.java.demo;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-31 09:57:57
 */
public class Java9Test {

    int state = 0;

    @Test
    void bitTest() {
//        System.out.println(state |= 1 << 0);
//        System.out.println(state |= 1 << 1);
//        System.out.println(state |= 1 << 2);
//        System.out.println(state |= 1 << 3);
        System.out.println(state |= 1 << 4);
    }
}