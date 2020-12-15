package com.xiaobei.java.demo.thread.chain2;

import java.lang.annotation.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-15 14:34:34
 */
@Inherited
@Documented
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface HandlerAnnotation {

    /**
     * 定义优先级
     * @return
     */
    int offset() default 0;
}
