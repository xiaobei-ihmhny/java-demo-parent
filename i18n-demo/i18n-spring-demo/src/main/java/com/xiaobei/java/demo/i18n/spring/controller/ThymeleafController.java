package com.xiaobei.java.demo.i18n.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-10 10:11:11
 */
@Controller
public class ThymeleafController {

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }
}