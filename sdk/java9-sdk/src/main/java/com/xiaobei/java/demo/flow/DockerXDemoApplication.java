package com.xiaobei.java.demo.flow;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/01 08:27
 */
public class DockerXDemoApplication {

    private void demoSubscribe(
            DockerXDemoPublisher<Integer> publisher, String subscriberName) {
        DockerXDemoSubscriber<Integer> subscriber = new DockerXDemoSubscriber<>(subscriberName,4L);
        publisher.subscribe(subscriber);
    }

    @Test
    public void customSubmissionPublisher() {
        ExecutorService executorService = ForkJoinPool.commonPool();// Executors.newFixedThreadPool(3);
        try(DockerXDemoPublisher<Integer> publisher = new DockerXDemoPublisher<>(executorService)) {
            demoSubscribe(publisher, "one");
            demoSubscribe(publisher, "two");
            demoSubscribe(publisher, "three");
            IntStream.range(1, 5).forEach(publisher::submit);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                executorService.shutdown();
                int shutdownDelaySec = 1;
                System.out.printf("....等待 %d 秒后结束服务....%n", shutdownDelaySec);
                executorService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.printf("捕获到 executorService.awaitTermination() 方法的异常, %s%n", e.getClass().getName());
            } finally {
                System.out.println("调用 executorService.shutdownNow() 方法结束服务...%n");
                List<Runnable> remainingTask = executorService.shutdownNow();
                System.out.printf("还剩 %d 个任务等待被执行，服务已关闭 %n", remainingTask.size());
            }
        }


    }
}
