package com.xiaobei.java.demo.i18n.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-10 10:11:11
 */
@Controller
public class ThymeleafController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }

    @ResponseBody
    @GetMapping(value = "/code/{code}")
    public String json(@PathVariable("code") String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}