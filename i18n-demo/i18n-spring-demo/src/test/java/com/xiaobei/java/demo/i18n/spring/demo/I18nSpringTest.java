package com.xiaobei.java.demo.i18n.spring.demo;

import org.junit.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-09 17:05:05
 */
public class I18nSpringTest {

    @Test
    public void testResouce() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader.getResource("").getPath());
        System.out.println(this.getClass().getResource("").getPath());
        System.out.println(this.getClass().getResource("/").getPath());
        System.out.println(System.getProperty("user.dir"));
    }
}