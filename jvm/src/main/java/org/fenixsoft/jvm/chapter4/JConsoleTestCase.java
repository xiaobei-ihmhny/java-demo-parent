package org.fenixsoft.jvm.chapter4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * VM args: -Xms100m -Xmx100m -XX:+UseSerialGC
 */
public class JConsoleTestCase {

    /**
     * 内存占位符对象，一个OOMObject大约占64K
     */
    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num, BufferedReader reader) throws InterruptedException, IOException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            // 稍作延时，令监视曲线的变化更加明显
            Thread.sleep(100);
            System.out.println("当前循环：" + i);
            list.add(new OOMObject());
        }
        // 方式一：
//        gc(reader);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        fillHeap(1000, reader);
        // 方式二：
//        gc(reader);
    }

    public static void gc(BufferedReader reader) throws IOException {
        reader.readLine();
        System.gc();
        reader.readLine();
    }

}
