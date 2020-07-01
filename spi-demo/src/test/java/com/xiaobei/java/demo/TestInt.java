package com.xiaobei.java.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/6/25 21:14
 */
public class TestInt {

    public static <T> List initList(List list, int n) {
        for (int i = 0; i < n; i++)
            list.add(i);
        return list;
    }

    @Test
    public void testForEach() {
        ArrayList<Integer> aList = (ArrayList<Integer>) initList(new ArrayList<>(), 20000000);
        long startTime = System.currentTimeMillis();
        for (Integer num : aList) {
            aList.get(num);
        }
        System.out.println(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < aList.size(); i++) {
            aList.get(i);
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static void main(String[] args) {
        int i = tableSizeFor(15);
        int b = tableSizeFor(100);
        int c = tableSizeFor(1000);
        System.out.println(i);
        System.out.println(b);
        System.out.println(c);
    }
}
