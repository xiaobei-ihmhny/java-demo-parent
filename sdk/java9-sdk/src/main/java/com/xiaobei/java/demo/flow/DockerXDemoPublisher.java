package com.xiaobei.java.demo.flow;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java9.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/01 07:44
 */
public class DockerXDemoPublisher<T> implements Flow.Publisher<T>, AutoCloseable {

    private final ExecutorService executor;// daemon-based

    private final CopyOnWriteArrayList<DockerXDemoSubscription<T>> list = new CopyOnWriteArrayList<>();

    public DockerXDemoPublisher(ExecutorService executor) {
        this.executor = executor;
    }

    public void submit(T item) {
        System.out.printf("********* 开始发布元素item：%s ***********%n", item);
        list.forEach(e -> {
            e.future = executor.submit(() -> e.subscriber.onNext(item));
        });
    }

    @Override
    public void close() throws Exception {
        list.forEach(e -> e.future = executor.submit(e.subscriber::onComplete));
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new DockerXDemoSubscription<>(subscriber, executor));
        list.add(new DockerXDemoSubscription<>(subscriber, executor));
    }

    static class DockerXDemoSubscription<T> implements Flow.Subscription {

        private final Flow.Subscriber<? super T> subscriber;

        private final ExecutorService executor;

        private Future<?> future;

        private T item;

        private boolean completed;

        public DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
        }

        @Override
        public void request(long n) {
            if(n !=0 && !completed) {
                if(n < 0) {
                    IllegalArgumentException ex = new IllegalArgumentException();
                    executor.execute(() -> subscriber.onError(ex));
                } else {
                    future = executor.submit(() -> subscriber.onNext(item));
                }
            } else {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            completed = true;
            if(future != null && !future.isCancelled()) {
                this.future.cancel(true);
            }
        }
    }
}
