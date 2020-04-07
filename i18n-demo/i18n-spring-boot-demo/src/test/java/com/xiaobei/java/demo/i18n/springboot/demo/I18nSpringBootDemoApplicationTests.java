package com.xiaobei.java.demo.i18n.springboot.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

@SpringBootTest
class I18nSpringBootDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testJavaI18n() {
        String welcome = ResourceBundle.getBundle(
                "i18n/messages", new Locale("en", "US")).getString("welcome");
        System.out.println(welcome);
        String welcome2 = ResourceBundle.getBundle(
                "i18n/messages", new Locale("zh", "CN")).getString("welcome");
        System.out.println(welcome2);
    }

    /**
     * 本地化工具类 之 {@link NumberFormat}
     */
    @Test
    void testLocalFormat() {
        Locale locale = new Locale("zh", "CN");
        NumberFormat currFmt = NumberFormat.getCurrencyInstance(locale);
        double amt = 1258683456.78;
        System.out.println(currFmt.format(amt));
        // 美国
        locale = new Locale("en", "US");
        currFmt = NumberFormat.getCurrencyInstance(locale);
        System.out.println(currFmt.format(amt));
        // 欧洲
        locale = Locale.ITALY;
        currFmt = NumberFormat.getCurrencyInstance(locale);
        System.out.println(currFmt.format(amt));
    }

    /**
     * 本地化工具类 之 {@link java.text.DateFormat}
     * 通过DateFormat#getDateInstance(int style,Locale locale)方法按本地化的方式对日期进行格式化操作。
     * 该方法第一个入参为时间样式，第二个入参为本地化对象。运行以上代码，输出以下信息
     */
    @Test
    void testLocalFormat2() {
        Date date = new Date();
        // 中国
        Locale locale = new Locale("zh", "CN");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        System.out.println(dateFormat.format(date));
        // 美国
        locale = new Locale("en", "US");
        dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        System.out.println(dateFormat.format(date));
        // 欧洲
        locale = Locale.ITALY;
        dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        System.out.println(dateFormat.format(date));
    }



}
