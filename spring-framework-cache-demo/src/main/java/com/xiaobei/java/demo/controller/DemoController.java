package com.xiaobei.java.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.cache.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-16 22:46:46
 */
@Controller
public class DemoController {

    private static Map<Long, String> TEST_MAP = new HashMap<>();

    static {
        TEST_MAP.put(1L, "xiaobei");
        TEST_MAP.put(2L, "huihui");
        TEST_MAP.put(3L, "beibei");
    }

    @RequestMapping("/get")
    @ResponseBody
    @CacheResult(cacheName = "jCache")
//    @Cacheable(cacheNames = "jCache", key = "#key")
    public String get(@CacheKey Long key) {
        System.out.println("进入get方法内部");
        return TEST_MAP.get(key);
    }

    @RequestMapping("/update")
    @ResponseBody
    @CachePut(cacheName = "jCache")
    public String updateHelloWorld(@CacheKey Long key, @CacheValue String value) {
        System.out.println("进入updateHelloWorld方法内部");
        TEST_MAP.put(key, value);
        return "hello, world";
    }

    @RequestMapping("/delete")
    @ResponseBody
    @CacheRemove(cacheName = "jCache")
    public String delete(@CacheKey Long key) {
        System.out.println("进入updateHelloWorld方法内部，删除key：[" + key + "] 成功");
        return "删除成功";
    }


}