package com.xiaobei.jdbc.demo.dbutils;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 指明当前 java 字段映射的数据库字段名
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/10 11:10
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ColumnName {

    /**
     * 指定数据库字段的名称
     * @return
     */
    @AliasFor("columnName")
    String value() default "";

    /**
     * 指定数据库字段的名称
     * @return
     */
    @AliasFor("value")
    String columnName() default "";
}
