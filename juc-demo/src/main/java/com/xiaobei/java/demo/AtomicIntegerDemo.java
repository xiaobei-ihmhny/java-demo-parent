package com.xiaobei.java.demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *     AtomicInteger是atomic框架中用的最多的原子类了。
 *     AtomicInteger是Integer类型的线程安全的原子类，
 *     可以在应用程序中以原子的方式更新int值。
 * </p>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-23 22:30:30
 */
public class AtomicIntegerDemo {

    private static final long valueOffset;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe)field.get(null);
            valueOffset = unsafe.objectFieldOffset
                    (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();

    }
}
