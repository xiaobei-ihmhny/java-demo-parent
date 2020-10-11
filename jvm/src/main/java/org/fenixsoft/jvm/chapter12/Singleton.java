package org.fenixsoft.jvm.chapter12;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/10/10 21:43
 */
public class Singleton {

    private volatile static Singleton instance;

    public static Singleton getInstance() {
        if(instance == null) {
            synchronized (Singleton.class) {
                if(instance == null) {
                    // TODO 如何解释不使用 volatile 是不安全的呢？
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Singleton.getInstance();
    }
}
