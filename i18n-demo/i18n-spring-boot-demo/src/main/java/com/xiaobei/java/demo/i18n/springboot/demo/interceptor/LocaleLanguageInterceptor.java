package com.xiaobei.java.demo.i18n.springboot.demo.interceptor;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-07 18:13:13
 */
public class LocaleLanguageInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        String[] split = servletPath.split("/");
        String endStr = split[split.length - 1];
        String lang = "zh";
        if("$setLang:en".equals(endStr)) {
            lang = "en";
        }
        System.out.printf("当前选择的语言是：%s", lang);
        // 获取当前使用的区域解析器LocaleResolver
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        assert localeResolver != null;
        if("zh".equals(lang)) {
            // 调用里面的方法 {@code setLocale}设置即可，
            // 这样的代码就是不管是会话还是cookie区域解析器都是一样的代码了。
            localeResolver.setLocale(request,response, new Locale("zh","CN"));
        } else if ("en".equals(lang)) {
            localeResolver.setLocale(request,response, new Locale("en","US"));
        }
        return true;
    }
}