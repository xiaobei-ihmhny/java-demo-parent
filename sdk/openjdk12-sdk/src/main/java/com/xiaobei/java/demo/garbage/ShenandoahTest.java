package com.xiaobei.java.demo.garbage;

import java.io.IOException;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/10/13 6:43
 */
public class ShenandoahTest {

    /**
     * // TODO 暂时无法直接使用：
     * 不过目前 Shenandoah 垃圾回收器还被标记为实验项目，
     * 如果要使用Shenandoah GC需要编译时 -with-jvm-features=-shenandoahgc，然后启动时使用参数
     * 启动参数：-XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("启动成功");
        System.in.read();
    }
}
