package com.xiaobei.java.demo.七_producer_consumer;

/**
 * 
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/26 7:16
 */
public class Main {

    /**
     * MakerThread-3 puts [ Cake No.0 by MakerThread-3]
     * EaterThread-3 takes [ Cake No.0 by MakerThread-3]
     * MakerThread-1 puts [ Cake No.1 by MakerThread-1]
     * EaterThread-1 takes [ Cake No.1 by MakerThread-1]
     * MakerThread-3 puts [ Cake No.2 by MakerThread-3]
     * EaterThread-2 takes [ Cake No.2 by MakerThread-3]
     * MakerThread-2 puts [ Cake No.3 by MakerThread-2]
     * EaterThread-3 takes [ Cake No.3 by MakerThread-2]
     * MakerThread-1 puts [ Cake No.4 by MakerThread-1]
     * @param args
     */
    public static void main(String[] args) {
        Table table = new Table(3);
        new MakerThread("MakerThread-1", table,12313).start();
        new MakerThread("MakerThread-2", table,34324).start();
        new MakerThread("MakerThread-3", table,98273).start();
        new EaterThread("EaterThread-1", table,12938).start();
        new EaterThread("EaterThread-2", table,82374).start();
        new EaterThread("EaterThread-3", table,98437).start();
    }
}
