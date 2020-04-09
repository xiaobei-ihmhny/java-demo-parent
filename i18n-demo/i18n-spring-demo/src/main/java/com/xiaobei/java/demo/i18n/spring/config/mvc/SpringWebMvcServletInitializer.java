package com.xiaobei.java.demo.i18n.spring.config.mvc;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/10 7:42
 */
public class SpringWebMvcServletInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return of(SpringWebMvcConfig.class);
    }

    @Override
    protected String[] getServletMappings() {
        return of("/*");
    }

    private static <T> T[] of(T... values) {
        return values;
    }
}
