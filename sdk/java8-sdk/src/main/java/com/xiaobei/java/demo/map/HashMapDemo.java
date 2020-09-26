package com.xiaobei.java.demo.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/25 20:13
 */
public class HashMapDemo {

    /**
     * <h2>当存放第一个元素时</h2>
     * // 新的容量大小
     * newCap = DEFAULT_INITIAL_CAPACITY;
     * // 新的阈值（达到该值将进行扩容）
     * newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
     * // 将阈值保存到实例变量 threshold 中
     * threshold = newThr;
     * // 创建底层数组
     * Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
     * // 将底层数组 table 指向新创建的数组
     * table = newTab;
     * // 返回新的数组
     * return newTab;
     *
     * 当存放第13个元素时再次进入 {@link }
     */
    @Test
    public void put() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "中");
        map.put("2", "华");
        map.put("3", "人");
        map.put("4", "民");
        map.put("5", "共");
        map.put("6", "和");
        map.put("7", "国");
        map.put("8", "万");
        map.put("9", "岁");
        map.put("10", "共");
        map.put("11", "产");
        map.put("12", "党");
        map.put("13", "万");
        map.put("14", "岁");
        System.out.println(map);
    }

    /**
     * 测试 hash 冲突
     *
     */
    @Test
    public void testHashImpact() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");//1 -> 1 + 1 << 4 = 17
        map.put("12", "12");//1 -> 1
        //=====================
        map.put("2", "2");// 2 -> 2 + 1 << 4 = 18
        map.put("13", "13");// 2 -> 2
        map.put("101", "101");// 2 -> 2 + 1 << 4 = 18
        map.put("112", "112");// 2 -> 2 + 1 << 4 = 18
        map.put("24", "24");// 2 -> 2
        //=====================
        map.put("3", "3");// 3 -> 3 + 1 << 4 = 20
        map.put("14", "14");// 5 -> 5 + 1 << 4 = 22
        //=====================
        map.put("5", "5");// 5 -> 4 + 1 << 4 = 21
        map.put("16", "16");// 5 -> 5
        //=====================
        map.put("7", "7");// 7 -> 4 + 1 << 4 = 23
        map.put("8", "8");// 8 -> 4 + 1 << 4 = 24
        System.out.println(map);
    }

}
