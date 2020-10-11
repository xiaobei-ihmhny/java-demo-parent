package org.fenixsoft.jvm.chapter13;

import java.util.Vector;

/**
 * @author zzm
 */
public class VectorTestCase_1 {

    private static Vector<Integer> vector = new Vector<Integer>();

    /**
     * 运行过程中会出现异常：
     * Exception in thread "Thread-2263" java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 6
     * 	at java.util.Vector.get(Vector.java:748)
     * 	at org.fenixsoft.jvm.chapter13.VectorTestCase_1$2.run(VectorTestCase_1.java:31)
     * 	at java.lang.Thread.run(Thread.java:745)
     * @param args
     */
    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        System.out.println((vector.get(i)));
                    }
                }
            });

            removeThread.start();
            printThread.start();

            //不要同时产生过多的线程，否则会导致操作系统假死
            while (Thread.activeCount() > 20) ;
        }
    }

}
