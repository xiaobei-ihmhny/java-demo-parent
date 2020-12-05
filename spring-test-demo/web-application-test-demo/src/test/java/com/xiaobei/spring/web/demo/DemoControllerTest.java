package com.xiaobei.spring.web.demo;

import com.xiaobei.spring.web.demo.controller.DemoController;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 17:41
 */
public class DemoControllerTest {

    /**
     * 测试 json 形式返回的请求
     * @throws Exception
     */
    @Test
    public void testHelloWorld() throws Exception {
        MockMvc mvc = MockMvcBuilders
                .standaloneSetup(DemoController.class)
                .build();
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/");
        String result = mvc.perform(requestBuilder)
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assert.assertEquals("Hello, World", result);
    }

    /**
     * TODO 模板测试暂未通过
     * @throws Exception
     */
    @Test
    public void testIndex() throws Exception {
        MockMvc mvc = MockMvcBuilders
                .standaloneSetup(DemoController.class)
                .build();
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders
                        .post("/index");
        ModelAndView modelAndView = mvc.perform(requestBuilder)
                .andReturn()
                .getModelAndView();
        Assert.assertNotNull(modelAndView);
        String viewName = modelAndView.getViewName();
        Assert.assertEquals("index", viewName);
    }
}
