package org.fenixsoft.jvm.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * staticObj、instanceObj、localObj存放在哪里？
 */
public class JHSDBTestCase {

    static class Test {
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done");    // 这里设一个断点
            try {
                TimeUnit.MINUTES.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ObjectHolder {}

    /**
     * VM参数：-Xmx10m -XX:+UseSerialGC -XX:-UseCompressedOops
     * @param args
     */
    public static void main(String[] args) {
        Test test = new Test();
        test.foo();
    }
}
