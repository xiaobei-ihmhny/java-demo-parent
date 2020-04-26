package com.xiaobei.java.demo.六_balking;

import java.io.IOException;

/**
 * 存储线程每个1s，会对数据进行一次保存，就像文本处理软件的“自动保存”一样。
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 14:01
 */
public class SaverThread extends Thread {

    private Data data;

    public SaverThread(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while(true) {
                data.save();// 存储资料
                Thread.sleep(1000);// 休息1秒钟
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
