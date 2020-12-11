package com.xiaobei.jdbc.demo;

import com.xiaobei.jdbc.demo.dbutils.AnnotationDemo;
import com.xiaobei.jdbc.demo.dbutils.ColumnName;
import com.xiaobei.jdbc.demo.domain.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/10 14:32
 */
public class AnnotationTest {

    @Test
    public void getTypeAnnotation() {
        AnnotationDemo annotation = User.class.getAnnotation(AnnotationDemo.class);
        if(annotation != null && !"".equals(annotation.value())) {
            final String value = annotation.value();
            System.out.println(value);
        }
        ColumnName columnName = User.class.getAnnotation(ColumnName.class);
        if(columnName != null && !"".equals(columnName.value())) {
            final String value = columnName.value();
            System.out.println(value);
        }
    }

    @Test
    public void getFieldAnnotation() {
        for (Field field : User.class.getDeclaredFields()) {
            AnnotationDemo annotation = field.getAnnotation(AnnotationDemo.class);
            if(annotation != null)
                System.out.println(annotation.value() + " -> " + field.getName());
        }

    }

    @Test
    public void getMethodAnnotation() {
        Method[] methods = User.class.getMethods();
        for (Method method : methods) {
            ColumnName columnName = method.getAnnotation(ColumnName.class);
            AnnotationDemo annotationDemo = method.getAnnotation(AnnotationDemo.class);
            if(columnName != null) {
                Type[] genericParameterTypes = method.getGenericParameterTypes();
                System.out.println(columnName.value());
            }
            if(annotationDemo != null) {
                System.out.println(annotationDemo.value());
            }
        }
    }
}
