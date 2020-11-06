package com.xiaobei.java.demo.map;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-09-29 14:45:45
 */
public class ConcurrentHashMapTest {

    @Test
    public void test() {
//        HashMap<String, String> map = new HashMap<>();
//        Map<String, String> sync = Collections.synchronizedMap(map);
        System.out.println(resizeStamp(16));
    }

    static int resizeStamp(int n) {
        int i = Integer.numberOfLeadingZeros(n);
        System.out.println(i);
        return i | (1 << (16 - 1));
    }
}