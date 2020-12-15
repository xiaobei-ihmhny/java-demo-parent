package com.xiaobei.java.demo.y2019;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-15 13:22:22
 */
public class ThreadTest {

    @Test
    public void extendsThread() {
        MyThread thread = new MyThread();
        thread.start();
        System.out.println(Thread.currentThread().getName() + " 启动线程完毕");
    }

    @Test
    public void implementRunnable() {
        Thread thread = new Thread(new TTThread());
        thread.start();
        System.out.println(Thread.currentThread().getName() + " 启动线程完毕");
    }

    /**
     * 执行结果为：Thread-0实现 Callable接口，获取一个包含返回值的线程
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void implementCallableWithFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(new HHThread());
        Thread thread = new Thread(task);
        thread.start();
        String result = task.get();
        System.out.println("执行结果为：" + result);
    }

    /**
     * pool-1-thread-1实现 Callable接口，获取一个包含返回值的线程
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void implementCallableWithExecutorService() throws ExecutionException, InterruptedException {
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(1);
        // 提交线程到线程池，并等待结果
        Future<String> result = pool.submit(new HHThread());
        System.out.println(result.get());
        // 关闭线程池
        pool.shutdown();

    }

    /**
     * TODO {@link FutureTask} 的实现原理？
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void futureTask() throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(new TTThread(), "1111");
        Thread thread = new Thread(task);
        thread.start();
        String result = task.get();
        System.out.println("执行结果为：" + result);

    }
}


/**
 * 通过继承 {@link Thread} 类，并重新其 {@link Thread#run()} 方法来实现多线程
 */
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}

/**
 * 通过实现 {@link Runnable} 接口，并重写其 {@link Runnable#run()} 方法来实现多线程
 */
class TTThread implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 通过实现 Runnable，实现创建线程");
    }
}

class HHThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        String result = Thread.currentThread().getName() + "实现 Callable接口，获取一个包含返回值的线程";
        TimeUnit.SECONDS.sleep(5);
        return result;
    }
}