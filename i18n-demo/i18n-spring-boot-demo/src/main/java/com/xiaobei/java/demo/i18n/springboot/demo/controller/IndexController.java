package com.xiaobei.java.demo.i18n.springboot.demo.controller;

import com.xiaobei.java.demo.i18n.springboot.demo.config.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("better")
    public String better() {
        String welcome = messageSourceService.getMessage("welcome");
        System.out.printf("获取到的信息为：%s\n", welcome);
        return "index";
    }
}
