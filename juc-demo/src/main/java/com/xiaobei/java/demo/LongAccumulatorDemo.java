package com.xiaobei.java.demo;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-01 07:45:45
 */
public class LongAccumulatorDemo {

    /**
     * {@link LongAccumulator#accumulate(long)}方法中的参数相当于
     * {@link LongBinaryOperator}函数中的right参数，其中第一个参数的初始值为base。
     * 下面方法的执行结果为：
     * 1
     * 7
     * 13
     * 19
     * 25
     *
     * @param args
     */
    public static void main(String[] args) {
        LongBinaryOperator accumulatorFunction = (l, r) -> l + 2*r;
        LongAccumulator la = new LongAccumulator(accumulatorFunction, 1);
        System.out.println(la.longValue());
        la.accumulate(3);
        System.out.println(la.longValue());
        la.accumulate(3);
        System.out.println(la.longValue());
        la.accumulate(3);
        System.out.println(la.longValue());
        la.accumulate(3);
        System.out.println(la.longValue());
    }
}
