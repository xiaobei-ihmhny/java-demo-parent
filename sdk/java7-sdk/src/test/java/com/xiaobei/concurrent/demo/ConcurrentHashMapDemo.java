package com.xiaobei.concurrent.demo;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/19 14:52
 */
public class ConcurrentHashMapDemo {

    @Test
    public void test() {
        Map<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "huihui");
        map.put(2, "tietie");
        map.put(3, "beibei");
        System.out.println(map);
        String value = map.get(1);
        System.out.println(value);
        Map<Integer, String> map2 = Collections.synchronizedMap(map);
    }
}
