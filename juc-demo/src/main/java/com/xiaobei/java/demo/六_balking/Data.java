package com.xiaobei.java.demo.六_balking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/25 10:54
 */
public class Data {

    private String filename;

    private String content;

    private boolean changed;

    public Data(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.changed = true;
    }

    public synchronized void change(String newContent) {
        content = newContent;
        changed = true;
    }

    public synchronized void save() throws IOException {
        if(!changed) {
            System.out.println(Thread.currentThread().getName() + " balks");
            return;
        }
        doSave();
        changed = false;
    }

    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName() + " calls doSave, content = " + content);
        Writer writer = new FileWriter(filename);
        writer.write(content);
        writer.close();
    }


}
