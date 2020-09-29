package com.xiaobei.java.demo.map;

import org.junit.Test;



/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-09-29 14:45:45
 */
public class ConcurrentHashMapTest {

    @Test
    public void test() {
        System.out.println(resizeStamp(16));
    }

    static int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (16 - 1));
    }
}