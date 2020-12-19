package com.xiaobei.java.demo.cyclicbarrier.sample2;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/19 07:31
 */
public class DataImportThreadDemo {

    private static final ThreadFactory NamedThreadFactory =
            new ThreadFactoryBuilder()
                    .setNameFormat("cyclicBarrier-pool-%d")
                    .build();

    private static final ExecutorService executorService = new ThreadPoolExecutor(
            5,
            10,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            NamedThreadFactory,
            new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * 开始导入第 1 部分的数据
     * 开始导入第 3 部分的数据
     * 开始导入第 2 部分的数据
     * 第 3 部分数据导入完成
     * 第 2 部分数据导入完成
     * 第 1 部分数据导入完成
     * 数据导入完成，开始进行数据分析
     * @param args
     */
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier =
                new CyclicBarrier(3, new FinishThread());
        executorService.submit(new DataImportThread(cyclicBarrier, "1"));
        executorService.submit(new DataImportThread(cyclicBarrier, "2"));
        executorService.submit(new DataImportThread(cyclicBarrier, "3"));
        //
        executorService.shutdown();
    }

    static class FinishThread implements Runnable {

        @Override
        public void run() {
            System.out.println("数据导入完成，开始进行数据分析");
        }
    }

    static class DataImportThread extends Thread {

        private final CyclicBarrier cyclicBarrier;

        private final String path;

        DataImportThread(CyclicBarrier cyclicBarrier, String path) {
            this.cyclicBarrier = cyclicBarrier;
            this.path = path;
        }

        @Override
        public void run() {
            System.out.printf("开始导入第 %s 部分的数据\n", path);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("第 %s 部分数据导入完成\n", path);
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


}
