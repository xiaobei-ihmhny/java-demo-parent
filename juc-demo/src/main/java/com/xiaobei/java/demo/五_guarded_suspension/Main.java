package com.xiaobei.java.demo.五_guarded_suspension;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 10:30
 */
public class Main {

    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();
        new ClientThread(requestQueue, "xiaobei", 3119832L).start();
        new ServerThread(requestQueue, "huihui", 110309L).start();
    }
}
