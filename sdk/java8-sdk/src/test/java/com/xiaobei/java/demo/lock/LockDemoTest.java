package com.xiaobei.java.demo.lock;

import org.junit.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-10-12 15:31:31
 */
public class LockDemoTest {

    @Test
    public void testLockCancel() {
        concatString("a","b","c");
    }

    public String concatString(String a, String b, String c) {
        return a + b + c;
    }
}