package com.xiaobei.java.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 测试ABA问题及其解决
 * // TODO {@link AtomicMarkableReference} 类的使用场景是什么，它又不能真正解决ABA问题！！！
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-27 07:50:50
 */
public class AtomicStampedReferenceDemo {

    /**
     * 执行结果如下：
     *
     * 操作线程干扰线程, [increment] ， 值=1
     * 操作线程主操作线程,初始值 = 1
     * 操作线程干扰线程, [decrement] ， 值=2
     * 操作线程 主操作线程，CAS操作结果：true
     * --------------分割线----------------
     * AtomicStampedReference操作线程主操作线程,初始值 = 1
     * AtomicStampedReference操作线程干扰线程, [increment] ， 值=2
     * AtomicStampedReference操作线程干扰线程, [increment] ， 值=1
     * AtomicStampedReference操作线程 主操作线程，CAS操作结果：false
     * --------------分割线----------------
     * AtomicMarkableReference操作线程主操作线程,初始值 = 1
     * AtomicMarkableReference操作线程干扰线程, [increment] ， 值=2
     * AtomicMarkableReference操作线程干扰线程, [increment] ， 值=1
     * AtomicMarkableReference操作线程 主操作线程，CAS操作结果：true
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // 演示ABA问题
        showABA();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("--------------分割线----------------");
        // 演示解决ABA问题
        showSolveABAByAtomicStampedReference();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("--------------分割线----------------");
        showSolveABAByAtomicMarkableReference();
    }


    //---------------------------- 以下为测试ABA问题 ----------------------------

    /**
     * 定义变量 a = 1
     */
    public static AtomicReference<Integer> a = new AtomicReference(1);
    public static void showABA() {
        Thread main = new Thread(()-> {
            Integer oldRef = a.get();
            // 定义
            System.out.println("操作线程" + Thread.currentThread().getName() + ",初始值 = " + oldRef);
            try {
                // 等待1秒，以便干扰线程执行
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // CAS操作
            boolean success = a.compareAndSet(1, 2);
            System.out.println("操作线程 " + Thread.currentThread().getName() + "，CAS操作结果：" + success);
        },"主操作线程");

        Thread other = new Thread(() -> {
            // 确保thread-main线程优先执行
            Thread.yield();
            Integer oldRef = a.get();
            // a 加 1 , 1 + 1 = 2
            a.compareAndSet(oldRef, oldRef+1);
            System.out.println("操作线程" + Thread.currentThread().getName() + ", [increment] ， 值=" +oldRef);
            oldRef = a.get();
            a.compareAndSet(oldRef, oldRef-1);
            System.out.println("操作线程" + Thread.currentThread().getName() + ", [decrement] ， 值=" +oldRef);
        },"干扰线程");

        main.start();
        other.start();
    }
    //---------------------------- 以上为测试ABA问题 ----------------------------

    //---------------------------- 以下为测试解决ABA问题 ----------------------------

    /**
     * 创建{@link AtomicStampedReference}对象，初始引用的Integer值为1，版本号为0
     */
    public static AtomicStampedReference<Integer> atomicStampedRef
            = new AtomicStampedReference<>(1, 0);
    public static void showSolveABAByAtomicStampedReference() {
        Thread main = new Thread(() -> {
            // 定义
            System.out.println("AtomicStampedReference操作线程" + Thread.currentThread().getName() + ",初始值 = " + atomicStampedRef.getReference());
            // 创建空数组用于保存当前标识
            int[] stamp = new int[1];
            // 调用get方法获取对象引用和对应的版本号
            Integer oldRef = atomicStampedRef.get(stamp);
            // stamp[0]保存了版本号
            int oldStamp = stamp[0];
            try {
                // 等待1秒，以便干扰线程执行
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 此时expectedReference未发生变化，但stamp已经被修改了，所以CAS失败
            boolean success = atomicStampedRef
                    .compareAndSet(oldRef, oldRef + 1, oldStamp, oldStamp + 1);
            System.out.println("AtomicStampedReference操作线程 " + Thread.currentThread().getName() + "，CAS操作结果：" + success);
        },"主操作线程");

        Thread other = new Thread(() -> {
            // 确保thread-main线程优先执行
            Thread.yield();
            // 创建空数组用于保存当前标识
            int[] stamp = new int[1];
            Integer oldRef1 = atomicStampedRef.get(stamp);
            int stamp1 = stamp[0];
            atomicStampedRef.compareAndSet(oldRef1, oldRef1 + 1, stamp1, stamp1 + 1);
            System.out.println("AtomicStampedReference操作线程" + Thread.currentThread().getName() + ", [increment] ， 值=" +atomicStampedRef.getReference());
            Integer oldRef2 = atomicStampedRef.get(stamp);
            int stamp2 = stamp[0];
            atomicStampedRef.compareAndSet(oldRef2, oldRef2 - 1, stamp2, stamp2 + 1);
            System.out.println("AtomicStampedReference操作线程" + Thread.currentThread().getName() + ", [increment] ， 值=" +atomicStampedRef.getReference());


        },"干扰线程");

        main.start();
        other.start();
    }


    /**
     * 创建{@link AtomicStampedReference}对象，初始引用的Integer值为1，版本号为0
     */
    public static AtomicMarkableReference<Integer> atomicMarkableRef
            = new AtomicMarkableReference<>(1, false);
    public static void showSolveABAByAtomicMarkableReference() {
        Thread main = new Thread(() -> {
            // 定义
            System.out.println("AtomicMarkableReference操作线程" + Thread.currentThread().getName() + ",初始值 = " + atomicMarkableRef.getReference());
            // 创建空数组用于保存当前标识
            boolean[] markHolder = new boolean[1];
            // 调用get方法获取对象引用和对应的版本号
            Integer oldRef = atomicMarkableRef.get(markHolder);
            // stamp[0]保存了版本号
            boolean oldMark = markHolder[0];
            try {
                // 等待1秒，以便干扰线程执行
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 此时expectedReference未发生变化，但stamp已经被修改了，所以CAS失败
            boolean success = atomicMarkableRef
                    .compareAndSet(oldRef, oldRef + 1, oldMark, !oldMark);
            System.out.println("AtomicMarkableReference操作线程 " + Thread.currentThread().getName() + "，CAS操作结果：" + success);
        },"主操作线程");

        Thread other = new Thread(() -> {
            // 确保thread-main线程优先执行
            Thread.yield();
            // 创建空数组用于保存当前标识
            boolean[] markHolder = new boolean[1];
            Integer oldRef1 = atomicMarkableRef.get(markHolder);
            boolean mark1 = markHolder[0];
            atomicMarkableRef.compareAndSet(oldRef1, oldRef1 + 1, mark1, !mark1);
            System.out.println("AtomicMarkableReference操作线程" + Thread.currentThread().getName() + ", [increment] ， 值=" +atomicMarkableRef.getReference());
            Integer oldRef2 = atomicMarkableRef.get(markHolder);
            boolean mark2 = markHolder[0];
            atomicMarkableRef.compareAndSet(oldRef2, oldRef2 - 1, mark2, !mark2);
            System.out.println("AtomicMarkableReference操作线程" + Thread.currentThread().getName() + ", [increment] ， 值=" +atomicMarkableRef.getReference());


        },"干扰线程");

        main.start();
        other.start();
    }


    //---------------------------- 以上为测试解决ABA问题 ----------------------------

}