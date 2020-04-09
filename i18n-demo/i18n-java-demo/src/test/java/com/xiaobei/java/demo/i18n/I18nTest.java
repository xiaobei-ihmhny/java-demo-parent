package com.xiaobei.java.demo.i18n;

import org.junit.Test;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-09 15:39:39
 */
public class I18nTest {

    /**
     * {@link Locale} 对象的创建示例
     */
    @Test
    public void demoLocale() {
        //①带有语言和国家/地区信息的本地化对象
        Locale locale1 = new Locale("zh","CN");
        //②只有语言信息的本地化对象
        Locale locale2 = new Locale("zh");
        //③等同于Locale("zh","CN")
        Locale locale3 = Locale.CHINA;
        //④等同于Locale("zh")
        Locale locale4 = Locale.CHINESE;
        //⑤获取本地系统默认的本地化对象
        Locale locale5= Locale.getDefault();
    }


    /**
     * 可以通过JVM的启动参数调整默认的本地化配置：-Duser.language=en -Duser.region=US
     */
    @Test
    public void testJavaI18n() {
        ResourceBundle rb1 = ResourceBundle.getBundle("resources", Locale.US);
        ResourceBundle rb2 = ResourceBundle.getBundle("resources", Locale.CHINA);
        Object[] params = {"John", new Date()};
        String str11 = new MessageFormat(rb1.getString("greeting.common2")).format(params);
        String str12 = new MessageFormat(rb1.getString("greeting.morning2")).format(params);
        String str13 = new MessageFormat(rb1.getString("greeting.afternoon2")).format(params);
        String str21 = new MessageFormat(rb2.getString("greeting.common2")).format(params);
        String str22 = new MessageFormat(rb2.getString("greeting.morning2")).format(params);
        String str23 = new MessageFormat(rb2.getString("greeting.afternoon2")).format(params);
        System.out.println(str11);
        System.out.println(str21);
        System.out.println(str12);
        System.out.println(str22);
        System.out.println(str13);
        System.out.println(str23);
    }

    /**
     * 本地化工具类 之 {@link NumberFormat}
     */
    @Test
    public void testNumberFormat() {
        // 默认情况下：
        double amt = 1258683456.78;
        NumberFormat defaultFmt = NumberFormat.getCurrencyInstance();
        System.out.println(defaultFmt.format(amt));
        // 中国
        Locale locale = new Locale("zh", "CN");
        NumberFormat currFmt = NumberFormat.getCurrencyInstance(locale);
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
    public void testDateFormat() {
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

    /**
     * {@link MessageFormat}在{@link NumberFormat}和{@link DateFormat}的基础上
     * 提供了强大的占位符字符串的格式化功能，
     * 它支持时间、货币、数字以及对象属性的格式化操作。
     *
     * 关于 的更多信息请参见javadoc：
     * <a href="https://docs.oracle.com/javase/7/docs/api/java/text/MessageFormat.html">MessageFormat</a>
     * 下面的实例演示了一些常见的格式化功能：
     */
    @Test
    public void testMessageFormat() {
        //①信息格式化串，通过{n}占位符指定动态参数的替换位置索引，{0}表示第一个参数，{1}表示第二个参数，以此类推。
        String pattern1 = "{0}，你好！你于{1}在工商银行存入{2} 元。";
        // 格式化信息串比较复杂一些，除参数位置索引外，还指定了参数的类型和样式。
        // 从pattern2中可以看出格式化信息串的语法是很灵活的，一个参数甚至可以出现在两个地方：
        // 如 {1,time,short}表示从第二个入参中获取时间部分的值，显示为短样式时间；
        // 而{1,date,long}表示从第二个入参中获取日期部分的值，显示为长样式时间。
        // 关于MessageFormat更详细的使用方法，请参见JDK的Javadoc
        String pattern2 = "At {1,time,short} On{1,date,long}，{0} paid {2,number, currency}.";

        //②用于动态替换占位符的参数
        Object[] params = {"John", new GregorianCalendar().getTime(), 1.0E3};

        //③使用默认本地化对象格式化信息
        String msg1 = MessageFormat.format(pattern1,params);

        //④使用指定的本地化对象格式化信息
        MessageFormat mf = new MessageFormat(pattern2,Locale.US);
        String msg2 = mf.format(params);
        System.out.println(msg1);
        System.out.println(msg2);
    }
}