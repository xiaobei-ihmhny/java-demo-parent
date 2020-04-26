package com.xiaobei.java.demo.五_guarded_suspension;

import java.util.LinkedList;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 10:15
 */
public class RequestQueue {

    private final LinkedList<Request> queue = new LinkedList<>();

    public synchronized Request getRequest() {
        while(queue.size() <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.removeFirst();
    }

    public synchronized void putRequest(Request request) {
        queue.addLast(request);
        notifyAll();
    }
}
