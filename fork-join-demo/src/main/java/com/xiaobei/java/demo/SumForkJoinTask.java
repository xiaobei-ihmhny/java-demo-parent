package com.xiaobei.java.demo;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * <a href="https://www.liaoxuefeng.com/article/1146802219354112">Java的Fork/Join任务，你写对了吗？</a>
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-10-05 07:41:41
 *
 * <p>当我们需要执行大量的小任务时，有经验的java开发人员都会采用线程池来执行这些小任务。
 * 然而，有一种任务，例如，对超过1000万个元素的数组进行排序，这种任务本身可以并发执行，
 * 但如何拆解成小任务需要在任务执行过程中动态拆分。这样，大任务可以拆成小任务，小任务
 * 还可以继续拆成更小的任务，最后把任务的结果汇总合并，得到最终的结果，这种模型就是
 * Fork/Join模型。
 * Java7 引入了Fork/Join框架，我们通过 {@link RecursiveTask}这个类就可以方便地实现Fork/Join模式。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class SumForkJoinTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 100;

    private long[] array;

    private int start;

    private int end;

    public SumForkJoinTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    /**
     * 编写这个Fork/Join的关键在于，在执行任务的 {@link SumForkJoinTask#compute()}方法内部，
     * 先判断任务是不是足够小，如果足够小，就直接计算并返回结果（模拟了1秒延时），否则，
     * 把任务一拆为二，分别计算两个子任务，再返回两个子任务的结果之和
     * <p>如果把
     * <pre>{@code {
     *     invokeAll(subTask1, subTask2);
     *     Long subResult1 = subTask1.join();
     *     Long subResult2 = subTask2.join();
     * }} 换成
     * </p>
     * {@code {
     *     // 分别对子任务调用fork()
     *     subTask1.fork();
     *     subTask2.fork();
     *     //合并结果
     *     Long subResult1 = subTask1.join();
     *     Long subResult2 = subTask2.join();
     *
     * }}</pre>
     * <p>
     *     这种写法就是错误的。
     *  这是因为执行 {@link SumForkJoinTask#compute()} 方法的线程本身也是一个Worker线程，
     *  当对两个子任务调用{@link ForkJoinTask#fork()}方法时，这个Worker线程就会
     *  把任务分配给另外两个Worker，但是它自己却停下来等待不干活了！这样就白白浪费了
     *  Fork/Join线程池中的一个Worker线程，导致了4个子任务至少需要7个线程才能并发执行。
     * </p>
     * @return the result of the computation
     */
    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 说明任务足够小，直接计算
            Long sum = computeDirectly();
            return sum;
        }
        // 任务太大，一分为二
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d ~ %d ===> %d ~ %d, %d ~ %d",start, end, start, middle, middle, end));
        SumForkJoinTask subTask1 = new SumForkJoinTask(array, start, middle);
        SumForkJoinTask subTask2 = new SumForkJoinTask(array, middle, end);
        invokeAll(subTask1, subTask2);
//        subTask1.fork();
//        subTask2.fork();
        Long subResult1 = subTask1.join();
        Long subResult2 = subTask2.join();
        Long result = subResult1 + subResult2;
        System.out.println(String.format("result = %d + %d ==> %d", subResult1, subResult2, result));
        return result;
    }

    /**
     * 直接计算
     * @return
     */
    public Long computeDirectly() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            try{
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sum += array[i];
        }
        System.out.println(String.format("thread %s compute %d ~ %d = %d",Thread.currentThread().getName() , start, end, sum));
        return sum;
    }
}
