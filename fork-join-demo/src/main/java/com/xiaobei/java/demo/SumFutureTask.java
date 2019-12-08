package com.xiaobei.java.demo;

import java.util.concurrent.Callable;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-02 12:40:40
 */
public class SumFutureTask implements Callable<Long> {

    private SumDirectly sumDirectly;

    public SumFutureTask(SumDirectly sumDirectly) {
        this.sumDirectly = sumDirectly;
    }

    @Override
    public Long call() throws Exception {
        return sumDirectly.computeDirectly();
    }
}