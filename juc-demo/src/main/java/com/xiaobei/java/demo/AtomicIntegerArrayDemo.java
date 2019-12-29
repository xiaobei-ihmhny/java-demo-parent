package com.xiaobei.java.demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-29 06:51:51
 */
public class AtomicIntegerArrayDemo {

    public static void main(String[] args) {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        // 将第0个元素原子地增加1
        array.getAndIncrement(0);

        AtomicInteger[] array2 = new AtomicInteger[10];
        // 将第0个元素原子地增加1
        array2[0].getAndIncrement();
    }

    private static void demo() throws Exception {
        Unsafe unsafe = getUnsafe();
        //
        int base = unsafe.arrayBaseOffset(int[].class);
        int scale = unsafe.arrayIndexScale(int[].class);
        int shift = Integer.numberOfLeadingZeros(scale);
        System.out.println("base:"+base+",scale:"+scale);
        /**
         * base = 16
         * scale = 4
         * 第一个元素：16 + 0 * 4
         * 第二个元素：16 + 1 * 4
         * 第三个元素：16 + 2 * 4
         */
    }

    private static Unsafe getUnsafe() throws Exception {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (Unsafe)field.get(null);
    }
}
