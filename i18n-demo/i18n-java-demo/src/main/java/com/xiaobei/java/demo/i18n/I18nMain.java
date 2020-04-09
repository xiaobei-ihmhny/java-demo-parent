package com.xiaobei.java.demo.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>如果应用系统中某些信息需要支持国际化功能，
 * 则必须为希望支持的不同本地化类型分别提供对应的资源文件，并以规范的方式进行命名。
 * 国际化资源文件的命名规范规定资源名称采用以下的方式进行命名：
 *
 * <资源名>_<语言代码>_<国家/地区代码>.properties
 *
 * 其中 <语言代码>与<国家/地区代码>是可选的。
 *
 * <p>
 *     优先级:
 *     假设资源名为resource，则语言为英文，国家为美国
 *     优先级从高到低为：
 *     resource_en_US.properties
 *     resource_en.properties
 *     resource.properties
 * </p>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-09 15:18:18
 */
public class I18nMain {

    public static void main(String[] args) {
        ResourceBundle resources = ResourceBundle.getBundle("resources", Locale.getDefault());
        String str = resources.getString("greeting.commontest");
        System.out.println(str);
    }
}