package com.xiaobei.java.demo.i18n.springboot.demo.controller;

import com.xiaobei.java.demo.i18n.springboot.demo.config.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/6 22:38
 */
@Controller
public class IndexController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MessageSourceService messageSourceService;

    @GetMapping("index")
    public String index() {
        Locale locale = LocaleContextHolder.getLocale();
        // 也可以使用 RequestContextUtils.getLocale(request);
        String welcome = messageSource.getMessage("welcome", null, locale);
        System.out.printf("获取到的信息为：%s\n", welcome);
        return "index";
    }

    @GetMapping("home")
    public String home() {
        Locale locale = LocaleContextHolder.getLocale();
        String welcome = messageSource.getMessage("home", null, locale);
        System.out.printf("获取到的信息为：%s\n", welcome);
        return "home";
    }

    @GetMapping("new/better")
    public String better() {
        String welcome = messageSourceService.getMessage("welcome");
        System.out.printf("获取到的信息为：%s\n", welcome);
        return "index";
    }

    /**
     * {@link org.springframework.web.servlet.i18n.SessionLocaleResolver} 相关配置
     * @param request
     * @param lang
     * @return
     */
    @GetMapping("/changeSessionLanguageBack")
    public String changeSessionLanguageBack(HttpServletRequest request, String lang) {
        System.out.printf("当前选择的语言是：%s", lang);
        if("zh".equals(lang)) {
            request.getSession().setAttribute(
                    SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh","CN"));
        } else if ("en".equals(lang)) {
            request.getSession().setAttribute(
                    SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en","US"));
        }
        return "redirect:/index";
    }

    /**
     * 针对 {@code SessionLocaleResolver} 和 {@code CookieLocaleResolver}
     * 同时有效
     * @param request
     * @param response
     * @param lang
     * @return
     */
    @GetMapping("/changeSessionLanguage")
    public String changeSessionLanguage(HttpServletRequest request,
                                        HttpServletResponse response, String lang) {
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
        return "redirect:/index";
    }
}
