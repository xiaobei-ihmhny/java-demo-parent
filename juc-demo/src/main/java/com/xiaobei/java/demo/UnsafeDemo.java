package com.xiaobei.java.demo;

import java.math.BigDecimal;
import java.util.*;

/**
 * // TODO https://segmentfault.com/a/1190000015815012 未完
 * <p>{@link sun.misc.Unsafe} 类来源于sun.misc包。该类封装了很多类似指针的操作，
 * 可以直接进行内存管理、操作对象、阻塞/唤醒线程等操作。java本身不直接支持指针操作，
 * 所以这也是该类命名为Unsafe的原因之一。
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-23 22:51:51
 *
 */
public class UnsafeDemo {

    /**
     * 如果对象中的字段值与期望的值相等，
     * 则将字段值修改为update，然后返回true；否则返回false;
     * Unsafe类中CAS方法都是native方法，需要通过CAS原子指令完成
     * @param object 需要修改的对象
     * @param offset 需要修改的字段到对象头的偏移量（通过偏移量可以快速定位修改的是哪个字段）
     * @param expected 期望值
     * @param update 要设置的值
     * @return 是否修改成功
     */
    public final native boolean compareAndSwapInt(Object object, long offset, int expected, int update);

    private static final int ASHIFT = 7;

    private static final int MMASK = 0xff;

    private static final int NCPU = Runtime.getRuntime().availableProcessors();

    static final int FULL = (NCPU >= (MMASK << 1)) ? MMASK : NCPU >>> 1;

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.setPrice(BigDecimal.valueOf(1.000));
        System.out.println(demo);
        System.out.println(BigDecimal.ONE.compareTo(demo.getPrice()));
        System.out.println(1 << 7);
        System.out.println(" mmask: "+ MMASK);
        System.out.println(" mmask << 1: "+ (MMASK << 1));
        System.out.println(" 1<<(31-ASHIFT): "+ (1<<(31-7)));
        System.out.println(" cpu>>>1: "+ (8>>>1));
        System.out.println(" 1 << 10: "+ (1 << 10));
        System.out.println("================");
        System.out.println("数组的起始大小：" + ((1 + 2) << ASHIFT));

        String[] seoArgs = new String[2];
        seoArgs[0] = "22";
        seoArgs[1] = "22";
    }
}
