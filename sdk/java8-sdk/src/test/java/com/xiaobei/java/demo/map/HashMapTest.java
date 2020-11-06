package com.xiaobei.java.demo.map;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/25 20:13
 */
public class HashMapTest {

    @Test
    public void test() {
        HashMap<String,String> map = new HashMap<>();
        map.put(null,null);
        System.out.println(map);
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("null", null);
        System.out.println(concurrentHashMap);
    }

    /**
     * <h2>运行结果：</h2>
     * 1
     * 16
     * 128
     * 1024
     * 16384
     *
     * 结果为比当前指定数字大的最小的2的n次幂
     */
    @Test
    public void tableSizeForTest() {
        System.out.println(tableSizeFor(1));
        System.out.println(tableSizeFor(10));
        System.out.println(tableSizeFor(100));
        System.out.println(tableSizeFor(1000));
        System.out.println(tableSizeFor(10000));
    }
    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @Test
    public void testHashCode() {
        isEquals("1", 9);
        isEquals("12", 9);
        isEquals("11", 9);
    }

    public void isEquals(String key, int n) {
        System.out.printf("hash(key) : %s, tableSizeFor(n) : %d, index : %d, newIndex: %d\n",
                Integer.toBinaryString(hash(key)), tableSizeFor(n), hash(key) & (tableSizeFor(n) - 1), hash(key) & (tableSizeFor(n)*2 - 1));
    }

    public int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    @Test
    public void testStringHashCode() {
        for (int i = 1; i < 10000; i++) {
            if((String.valueOf(i).hashCode() & 15) == 2) {
                System.out.println("map.put(\"" + i + "\", \"" + i + "\");");
            }
        }
    }

    @Test
    public void testResize() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("2", "2");// 2 + 1 << 4 = 18
        map.put("13", "13");// 2
        map.put("101", "101");// 2 + 1 << 4 = 18
        map.put("112", "112");// 2 + 1 << 4 = 18
        map.put("24", "24");// 2
        map.put("123", "123");// 2 + 1 << 4 = 18
        map.put("35", "35");// 2
        Map<String, Integer> oldDist = new HashMap<>(8);
        Map<String, Integer> newDist = new LinkedHashMap<>(128);
        for(Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            oldDist.put(key, (String.valueOf(key).hashCode() & (1 << 4) - 1));
            int result;
            if((result = (String.valueOf(key).hashCode() & (1 << 5) - 1)) != 2) {
                newDist.put(key, result);
            }
        }
        for(Map.Entry<String, Integer> entry : oldDist.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value != 2) {
                System.out.println("oldDist：" + key + " - " + value);
            }
        }
        Set<Map.Entry<String, Integer>> entries = newDist.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("newDist：" + key + " - " + value);
        }
//        map.forEach((key, value) -> {
//            oldDist.put(key, (String.valueOf(key).hashCode() & (1 << 4) - 1));
//            newDist.put(key, (String.valueOf(key).hashCode() & (1 << 5) - 1));
//        });
//        Stream.of(oldDist).forEach(System.out::println);
//        Stream.of(newDist).forEach(System.out::println);
    }

}
