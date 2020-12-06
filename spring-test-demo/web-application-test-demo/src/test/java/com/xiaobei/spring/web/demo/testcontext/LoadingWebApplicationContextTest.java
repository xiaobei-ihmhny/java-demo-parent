package com.xiaobei.spring.web.demo.testcontext;


import com.xiaobei.spring.web.demo.config.SpringWebMvcConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 16:25
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringWebMvcConfiguration.class)
@WebAppConfiguration
public class LoadingWebApplicationContextTest {

    @Autowired
    private WebApplicationContext wac;// cached

    @Autowired
    private MockServletContext servletContext;// cached

    @Autowired
    private MockHttpSession session;

    @Autowired
    private MockHttpServletRequest request;

    @Autowired
    private MockHttpServletResponse response;

    @Autowired
    private ServletWebRequest webRequest;

    /**
     * 以上的 {@link Autowired} 标注的对象将被正常的注入
     * TODO 这些类该如何使用呢？
     */
    @Test
    public void test() {
        System.out.println(1111);
    }

}
