package com.xiaobei.general.spring.demo.envirenmentprofiles;

import com.xiaobei.general.spring.demo.domain.MyDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ActiveProfilesResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextAnnotationUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.*;

/**
 * 通过自定义 Profile 的激活条件（{@link org.springframework.test.context.ActiveProfilesResolver}），
 * 来替换原有激活配置。本例中使用自定义注解 {@link MyAnnotation} 来实现环境的配置
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 13:44
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/app-config.xml")
@ActiveProfiles(
        resolver = EnvironmentProfilesFromCustomTest.CustomActiveProfilesResolver.class,
        inheritProfiles = false)
@EnvironmentProfilesFromCustomTest.MyAnnotation("dev1")
public class EnvironmentProfilesFromCustomTest {

    /**
     * 实现自己的激活环境配置逻辑
     */
    static class CustomActiveProfilesResolver implements ActiveProfilesResolver {

        @Override
        public String[] resolve(Class<?> testClass) {
            TestContextAnnotationUtils.AnnotationDescriptor<MyAnnotation> descriptor =
                    TestContextAnnotationUtils.findAnnotationDescriptor(testClass, MyAnnotation.class);
            if(descriptor != null) {
                MyAnnotation annotation = descriptor.getAnnotation();
                return annotation.value();
            }
            return new String[0];
        }
    }

    /**
     * 自定义注解
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    @interface MyAnnotation {

        String[] value() default {};
    }


    @Autowired
    private MyDataSource myDataSource;

    /**
     * MyDataSource{url='mysql:jdbc://127.0.0.1:3306/db_dev1', username='root', password='root'}
     */
    @Test
    public void activeProfilesTest() {
        System.out.println(myDataSource);
    }
}
