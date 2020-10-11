package com.xiaobei.java.demo;

import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 纤程 demo 学习
 * <a href="https://wiki.openjdk.java.net/display/loom/Getting+started">loom项目快速开始</a>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/10/11 23:03
 */
public class LoomDemo {

    /**
     * 运行结果：Hello
     *
     * @throws InterruptedException
     */
    @Test
    public void demo1() throws InterruptedException {
        Thread thread = Thread.startVirtualThread(() -> System.out.println("Hello"));
        thread.join();
    }

    /**
     * The Thread.Builder API can also be used to create virtual threads
     * that are configured at build time.
     * The first snippet below creates an un-started thread.
     * The second snippet creates and starts a thread with name "bob".
     */
    @Test
    public void demo2() {
        Thread thread1 =
                Thread.builder()
                        .virtual()
                        .task(() -> System.out.println("Hello"))
                        .build();
        Thread thread2 = Thread.builder().virtual()
                .name("bob")
                .task(() -> System.out.println("I'm Bob!"))
                .start();

        // 等待线程执行完成
        List<Thread> list = new ArrayList<>();
        list.add(thread1);
        list.add(thread2);
        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * The following creates an ExecutorService that runs each task in its own virtual thread.
     * The example uses the try-with-resources construct to ensure that
     * the ExecutorService is shutdown and that
     * the two tasks (each run in its own virtual thread) complete before continuing.
     */
    @Test
    public void demo3() {
        try (ExecutorService executorService = Executors.newVirtualThreadExecutor()) {
            executorService.execute(() -> System.out.println("Hello"));
            executorService.execute(() -> System.out.println("Hi"));
        }
    }

    /**
     * The example below runs three tasks and selects the result of the first task to complete.
     * The remaining tasks are cancelled, which causes the virtual threads running them to be interrupted.
     */
    @Test
    public void demo4() throws ExecutionException, InterruptedException {
        try (ExecutorService executor = Executors.newVirtualThreadExecutor()) {
            Callable<String> task1 = () -> "foo";
            Callable<String> task2 = () -> "bar";
            Callable<String> task3 = () -> "baz";
            String result = executor.invokeAny(List.of(task1, task2, task3));
            System.out.println(result);
        }
    }

    /**
     * This following example uses submitTasks to submit three value returning tasks.
     * It uses the CompletableFuture.completed method to obtain a stream
     * that is lazily populated as the tasks complete.
     */
    @Test
    public void demo5() {
        try (ExecutorService executor = Executors.newVirtualThreadExecutor()) {
            Callable<String> task1 = () -> "foo";
            Callable<String> task2 = () -> "bar";
            Callable<String> task3 = () -> "baz";
            List<CompletableFuture<String>> cfs = executor.submitTasks(List.of(task1, task2, task3));
            CompletableFuture.completed(cfs)
                    .map(CompletableFuture::join)
                    .forEach(System.out::println);
        }
    }

    /**
     * The following creates an ExecutorService that runs each task in its own virtual thread with a deadline.
     * If the deadline expires before the executor has terminated
     * then the thread executing this code will be interrupted and any tasks running will be cancelled.
     * (which causes the virtual thread to be interrupted).
     */
    @Test
    public void demo6() {
        Instant deadline = Instant.now().plusSeconds(30);
        try (ExecutorService executor = Executors.newVirtualThreadExecutor().withDeadline(deadline)) {

        }
    }
}
