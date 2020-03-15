package com.xiaobei.java.demo.threadlocal;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-08 14:02:02
 */
public class TSLog {

    private PrintWriter writer = null;

    public TSLog(String filename) {
        try {
            writer = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void println(String s) {
        writer.println(s);
    }

    public void close() {
        writer.println("=====End of log=====");
        writer.close();
    }
}