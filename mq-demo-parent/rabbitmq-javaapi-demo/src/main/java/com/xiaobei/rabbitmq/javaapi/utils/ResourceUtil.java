package com.xiaobei.rabbitmq.javaapi.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通过 {@link ResourceBundle} 来获取配置信息
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-12 22:38:38
 */
public class ResourceUtil {

    private static final ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("rabbitmq");
    }

    public static String getKey(String key) {
        String originalValue = bundle.getString(key);
        // TODO 替换 originalValue 中的占位符
        Pattern pattern = Pattern.compile("(\\$\\{[^\\{]\\})");
        Matcher matcher = pattern.matcher(originalValue);
        if(matcher.find()) {
            System.out.println(matcher.group(0));
        }
        // ${所在下标 -> } 所在下标
        Map<Integer, Integer> map = new HashMap<>(5 - 1);
        return originalValue;
    }
}
