package com.xiaobei.java.demo.locks;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/3 7:08
 */
public class RWTreeMapTest {

    private static Map<String, String> map = new TreeMap<>();

    static {
        map.put("a","a");
        map.put("c","c");
        map.put("b","b");
    }

    public void testNonSafe() {
        
    }
}
