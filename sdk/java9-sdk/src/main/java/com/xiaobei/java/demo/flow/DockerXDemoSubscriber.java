package com.xiaobei.java.demo.flow;

import java9.util.concurrent.Flow;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/01 07:34
 */
public class DockerXDemoSubscriber<T> implements Flow.Subscriber<T> {

    private String name;

    private Flow.Subscription subscription;

    final long bufferSize;

    long count;

    public String getName() {
        return name;
    }

    public Flow.Subscription getSubscription() {
        return subscription;
    }

    public DockerXDemoSubscriber(String name, long bufferSize) {
        this.name = name;
        this.bufferSize = bufferSize;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        // count = bufferSize - bufferSize / 2;
        // 在消费一半的时候重新请求
        (this.subscription = subscription).request(bufferSize);
        System.out.println("开始 onSubscribe 订阅");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T item) {
        if (--count <= 0) subscription.request(count = bufferSize - bufferSize / 2);
        System.out.printf(" ##### %s name: %s item: %s #####%n", Thread.currentThread().getName(), name, item);
        System.out.println(name + " received: " + item);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println(" Completed ");
    }
}
