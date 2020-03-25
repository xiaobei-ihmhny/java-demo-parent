package com.xiaobei.java.demo.controller;

import com.xiaobei.java.demo.ehcache.EhcacheResultException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.cache.annotation.*;
import java.time.LocalDateTime;
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

    /**
     * TODO 暂时不知道 {code exceptionCacheName} 及 {@code cachedExceptions } 如何使用
     * @param key
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    @CacheResult(cacheName = "jCache")
    public String getByJSR107(@CacheKey Long key) {
        System.out.println("进入get方法内部"+ LocalDateTime.now());
        return TEST_MAP.get(key);
    }

    @RequestMapping("get2")
    @ResponseBody
    @Cacheable(cacheNames = "jCache", key = "#key")
    public String getBySpringCache(Long key) {
        System.out.println("进入 getBySpringCache 方法内部"+ LocalDateTime.now());
        return TEST_MAP.get(key);
    }

    @RequestMapping("/update")
    @ResponseBody
    @CachePut(cacheName = "jCache")
    public String updateByJSR107(@CacheKey Long key, @CacheValue String value) {
        System.out.println("进入updateHelloWorld方法内部");
        TEST_MAP.put(key, value);
        return "hello, world";
    }

    @RequestMapping("update2")
    @ResponseBody
    @org.springframework.cache.annotation.CachePut(cacheNames = "jCache", key = "#key")
    public String updateBySpringCache(Long key, String value) {
        return null;
    }

    @RequestMapping("/delete")
    @ResponseBody
    @CacheRemove(cacheName = "jCache")
    public String deleteByJSR107(@CacheKey Long key) {
        System.out.println("进入updateHelloWorld方法内部，删除key：[" + key + "] 成功");
        return "删除成功";
    }


}