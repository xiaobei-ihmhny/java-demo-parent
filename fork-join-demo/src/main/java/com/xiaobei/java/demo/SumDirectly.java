package com.xiaobei.java.demo;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-02 11:47:47
 */
@SuppressWarnings("ALL")
public class SumDirectly {

    private long[] array;

    private int start;

    private int end;

    public SumDirectly(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public long[] getArray() {
        return array;
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