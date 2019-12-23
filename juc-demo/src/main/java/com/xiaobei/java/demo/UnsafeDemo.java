package com.xiaobei.java.demo;

/**
 * // TODO https://segmentfault.com/a/1190000015815012 未完
 * <p>{@link sun.misc.Unsafe} 类来源于sun.misc包。该类封装了很多类似指针的操作，
 * 可以直接进行内存管理、操作对象、阻塞/唤醒线程等操作。java本身不直接支持指针操作，
 * 所以这也是该类命名为Unsafe的原因之一。
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-23 22:51:51
 */
public class UnsafeDemo {

    /**
     * 如果对象中的字段值与期望的值相等，
     * 则将字段值修改为update，然后返回true；否则返回false;
     * @param object 指定的对象
     * @param offset 对象中的字段的偏移量
     * @param expected 对象改变前的期望值
     * @param update 对象满足期望值时要修改的值
     * @return 是否修改成功
     */
    public final native boolean compareAndSwapInt(Object object, long offset, int expected, int update);
}
