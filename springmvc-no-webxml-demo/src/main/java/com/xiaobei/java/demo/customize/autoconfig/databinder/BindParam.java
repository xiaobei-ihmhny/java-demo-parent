package com.xiaobei.java.demo.customize.autoconfig.databinder;

import java.lang.annotation.*;

/**
 * 请求参数绑定
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-19 10:13:13
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindParam {

    String name();
}
