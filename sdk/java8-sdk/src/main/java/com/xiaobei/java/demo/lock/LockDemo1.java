package com.xiaobei.java.demo.lock;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-10-12 15:59:59
 */
public class LockDemo1 {

    private Object object = new Object();

    /**
     * 使用命令：javap -c LockDemo1.class
     * TODO 为什么会有两个 monitorexit？
     * 结果：
     * public void method();
     *     Code:
     *        0: aload_0
     *        1: getfield      #3                  // Field object:Ljava/lang/Object;
     *        4: dup
     *        5: astore_1
     *        6: monitorenter
     *        7: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *       10: ldc           #5                  // String hello world
     *       12: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     *       15: aload_1
     *       16: monitorexit
     *       17: goto          25
     *       20: astore_2
     *       21: aload_1
     *       22: monitorexit
     *       23: aload_2
     *       24: athrow
     *       25: return
     *  从结果中可以看出，进行了同步操作（monitorenter、monitorexit）
     */
    public void method() {
        synchronized (object) {
            System.out.println("hello world");
        }
    }
}