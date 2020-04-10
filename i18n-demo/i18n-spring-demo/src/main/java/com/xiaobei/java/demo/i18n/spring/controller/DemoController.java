package com.xiaobei.java.demo.i18n.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/4/10 7:46
 */
@RestController
public class DemoController {

    @GetMapping("test")
    public String test() {
        return "hello world";
    }


}
