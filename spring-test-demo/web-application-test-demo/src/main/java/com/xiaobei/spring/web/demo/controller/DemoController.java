package com.xiaobei.spring.web.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 07:30
 */
@Controller
public class DemoController {

    @ResponseBody
    @RequestMapping("/")
    public String helloWorld() {
        return "Hello, World";
    }


    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("message", "xiaobei");
        return "index";
    }
}
