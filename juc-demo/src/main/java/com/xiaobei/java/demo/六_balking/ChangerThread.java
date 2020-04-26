package com.xiaobei.java.demo.六_balking;

import java.io.IOException;
import java.util.Random;

/**
 * 修改线程，模仿“一边修改文章，一边保存”
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 13:58
 */
public class ChangerThread extends Thread {

    private Data data;

    private Random random = new Random();

    public ChangerThread(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            for (int i = 0;; i++) {
                data.change("No." + i);
                Thread.sleep(5000);
                data.save();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
