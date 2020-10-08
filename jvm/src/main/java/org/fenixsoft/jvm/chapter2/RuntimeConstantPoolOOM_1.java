package org.fenixsoft.jvm.chapter2;

import java.util.HashSet;
import java.util.Set;

/**
 * VM Args：-XX:PermSize=6M -XX:MaxPermSize=6M
 * jdk版本 6 之前和之后的版本运行结果会出现不同
 *
 * @author zzm
 */
public class RuntimeConstantPoolOOM_1 {

    /**
     * TODO 在short范围内足以让6MB的PermSize产生OOM了???
     * @param args
     */
    public static void main(String[] args) {
        // 使用Set保持着常量池引用，避免Full GC回收常量池行为
        Set<String> set = new HashSet<String>();
        // 在short范围内足以让6MB的PermSize产生OOM了
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}
