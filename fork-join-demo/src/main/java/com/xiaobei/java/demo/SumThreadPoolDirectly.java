package com.xiaobei.java.demo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-02 11:54:54
 */
@SuppressWarnings("ALL")
public class SumThreadPoolDirectly {

    private static final int THRESHOLD = 100;

    private ThreadFactory namedThreadFactory =
            new ThreadFactoryBuilder().setNameFormat("%d_Thread").build();

    private ExecutorService threadPool =
            new ThreadPoolExecutor(
                    4,
                    10,
                    60L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(1024),
                    namedThreadFactory,
                    new ThreadPoolExecutor.AbortPolicy());

    public Long compute(SumDirectly sumDirectly) throws ExecutionException, InterruptedException {
        long sum = 0;
        long[] array = sumDirectly.getArray();
        int start = sumDirectly.getStart();
        int end = sumDirectly.getEnd();
        int threadNums = array.length / THRESHOLD + 1;
        List<Future<Long>> taskList = new ArrayList<>();
        for(int i = 0; i < threadNums; i++) {
            int currentStart = i*THRESHOLD;
            int currentEnd = (currentStart + THRESHOLD) > array.length
                    ? array.length
                    : (currentStart + THRESHOLD);
            SumDirectly directly = new SumDirectly(array, currentStart, currentEnd);
            FutureTask<Long> futureTask = new FutureTask<>(new SumFutureTask(directly));
            taskList.add(futureTask);
            threadPool.submit(futureTask);
            System.out.println(String.format("当前计算区间为： %d ~ %d ",currentStart, currentEnd));
        }
        for (Future<Long> future : taskList) {
            Long result = future.get();
            sum += result;
        }
        System.out.println(String.format("计算结果为： %d",sum));
        return sum;
    }
}
