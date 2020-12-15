package com.xiaobei.java.demo.thread.chain1;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-15 14:06:06
 */
public class MainDemo {

    PrintProcessor printProcessor;

    protected MainDemo() {
        SaveProcessor saveProcessor = new SaveProcessor();
        saveProcessor.start();
        printProcessor = new PrintProcessor(saveProcessor);
        printProcessor.start();
    }

    private void doTest(Request request) {
        printProcessor.processRequest(request);
    }

    /**
     * 执行时间太慢，应该考虑使用线程池解决
     * @param args
     */
    public static void main(String[] args) {
        MainDemo mainDemo = new MainDemo();
        for (int i = 0; i < 100; i++) {
            Request request = new Request();
            request.setName("TT" + i);
            mainDemo.doTest(request);
        }

    }
}