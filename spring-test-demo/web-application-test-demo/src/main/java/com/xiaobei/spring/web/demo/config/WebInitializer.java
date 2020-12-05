package com.xiaobei.spring.web.demo.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 配置 {@link org.springframework.web.servlet.DispatcherServlet}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 07:53
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 获取 Spring 应用容器的配置文件
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /**
     * 负责获取 Spring MVC 应用容器，这里传入预先定义好的 {@link SpringWebMvcConfiguration}
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return of(SpringWebMvcConfiguration.class);
    }

    /**
     * 指定 {@link org.springframework.web.servlet.DispatcherServlet} 映射路径
     * 这里指定是 "/*"，意为拦截所有请求路径
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return of("/*");
    }

    private static <T> T[] of(T... values) {
        return values;
    }
}
