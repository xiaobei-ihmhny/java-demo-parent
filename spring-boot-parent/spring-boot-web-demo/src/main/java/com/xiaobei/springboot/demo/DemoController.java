package com.xiaobei.springboot.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-05-23 11:44:44
 */
@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("index")
    public String helloWorld() {
        String uuid = UUID.randomUUID().toString();
        for (int i = 0; i < 1000; i++) {
            LOGGER.info(uuid + " ===> hello world + " + i);
        }
        return "hello world";
    }
}
