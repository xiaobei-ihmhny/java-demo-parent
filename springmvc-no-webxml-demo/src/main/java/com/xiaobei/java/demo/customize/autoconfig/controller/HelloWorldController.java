package com.xiaobei.java.demo.customize.autoconfig.controller;

import com.xiaobei.java.demo.customize.autoconfig.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-15 15:57:57
 */
@Controller
public class HelloWorldController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class.getName());

    private final Map<Long, User> userMap = new HashMap<>(16);

    @RequestMapping
    @ResponseBody
    public String helloWorld() {
        for (int i = 0; i < 1000; i++) {
            LOGGER.info("hello world_" + i);
        }
        return "hello world";
    }

    @RequestMapping(value = "/path/{param1}/{param2}",method = RequestMethod.GET)
    @ResponseBody
    public String mapParams(@PathVariable("param1") String param1,
                            @PathVariable("param2") String param2,
                            HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    @ResponseBody
    @RequestMapping(value = "/header",method = RequestMethod.GET, headers = {"password=xiaohui"})
    public String customHeaders(String name, HttpServletResponse response) {
        response.addHeader("answer", "beibei");
        return "this is header request, your header consider the current header named password and value xiaohui: " + name;
    }

    @ResponseBody
    @RequestMapping(value = "/user/save",method = RequestMethod.POST)
    public String postForObject(User user) {
        LOGGER.info("用户信息为：" + user);
        userMap.put(user.getId(), user);
        return "user信息保存成功";
    }

    @ResponseBody
    @RequestMapping(value = "/user/{userId}",method = RequestMethod.GET)
    public User getForObject(@PathVariable("userId") Long userId) {
        LOGGER.info("用户信息为：" + userId);
        return userMap.get(userId);
    }

    @ResponseBody
    @RequestMapping(value = "/user/save/valid",method = RequestMethod.POST)
    public String postForObjectValid(/*@Valid */User user, BindingResult result) {
        LOGGER.info("用户信息为：" + user);
        userMap.put(user.getId(), user);
        return "user信息保存成功";
    }
}