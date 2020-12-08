package com.xiaobei.java.demo.map;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


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

    @Test
    public void computeIfAbsent() {
        Map<String, Set<String>> map = new ConcurrentHashMap<>();
        Set<String> set = map.computeIfAbsent("1", k -> new LinkedHashSet<>(8));
        boolean result = set.add("2");
        Set<String> set2 = map.computeIfAbsent("1", k -> new LinkedHashSet<>(8));
        boolean result2 = set.add("2");
        // computeIfAbsent 相当于下面的功能
        Set<String> tempSet = map.get("1");
        if(tempSet == null) {
            tempSet = new LinkedHashSet<>(8);
        }
        tempSet.add("2");
        map.put("1", tempSet);
        System.out.println(map);
    }
}