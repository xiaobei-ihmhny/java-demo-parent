package com.xiaobei.spring.web.demo;

import com.xiaobei.spring.web.demo.controller.DemoController;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 参考文章：<a href="https://blog.csdn.net/qq_16513911/article/details/83018027">Spring Boot Junit单元测试MockMvc使用</a>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 09:15
 */
public class MockTest {

    @Test
    public void mockHttpServletRequestBuilderTest() throws IOException {
        // post请求
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/index");
        // get 请求
        MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/");
        // post 请求 带 Cookie
        MockHttpServletRequestBuilder postWithCookie =
                MockMvcRequestBuilders.post("/index")
                .cookie(new Cookie("cookieName1", "value1"),
                        new Cookie("cookieName2", "value2"));
        // get 请求带 Cookie
        MockHttpServletRequestBuilder getWithCookie =
                MockMvcRequestBuilders.get("/index")
                        .cookie(new Cookie("cookieName1", "value1"),
                                new Cookie("cookieName2", "value2"));
        // post 请求 带session
        MockHttpServletRequestBuilder postWithSession =
                MockMvcRequestBuilders.post("/index")
                        .sessionAttr("sessionName1", "value1")
                        .sessionAttr("sessionName2", "value2");

        // post 请求 带session
        MockHttpServletRequestBuilder getWithSession =
                MockMvcRequestBuilders.get("/index")
                        .sessionAttr("sessionName1", "value1")
                        .sessionAttr("sessionName2", "value2");
        //post请求    带Cookie     带参
        //另外也可适用场景 使用@ModelAttribute("formName")注解接收form表单对象
        //例子:
        //    @PostMapping("/submitOrder")
        //    public ModelAndView submitOrder (@ModelAttribute("orderForm") ServiceProductOrder serviceProductOrder)
        MockHttpServletRequestBuilder postCookiePar = MockMvcRequestBuilders.post("/test")
                .cookie(new Cookie("cookieName","value"))//自己填键值对
                .param("userName","admin")//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
                .param("pass","admin");//用@ModelAttribute注解的直接对应表单内容

        //get请求     带Cookie     带参方法一(用方法填充)
        MockHttpServletRequestBuilder getCookieParOne = MockMvcRequestBuilders
                .post("/test")
                .cookie(new Cookie("cookieName","value"))//自己填键值对
                //有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
                .param("userName","admin")
                .param("pass","admin");
        //get请求     带Cookie     带参方法二(url路径拼接)
        MockHttpServletRequestBuilder getCookieParTwo = MockMvcRequestBuilders
                .post("/test?userName=admin&pass=admin")
                .param("pass","admin");


        //post请求    带Cookie
        //适用场景:使用@RequestBody注解接收对象
        //例子:@PostMapping("/submitOrder")
        //     public ModelAndView submitOrder (@RequestBody ServiceProductOrder serviceProductOrder) {
//        Admin  admin=new Admin();
//        admin.setLoginName("admin");
//        admin.setLoginPassword("admin");//填一些必要的参数等.
//        MockHttpServletRequestBuilder post_cookie_obj = MockMvcRequestBuilders.post("/test")
//                .cookie(new Cookie("cookieName","value"))//自己填键值对
//                .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(admin));//阿里巴巴的json序列化

        //MultipartFile文件上传请求
        String filename = "images/sy_02.png";//测试文件
        InputStream inStream = getClass().getClassLoader().getResourceAsStream(filename);
        MockMultipartFile mfile = new MockMultipartFile("file", "sy_02.png", "png", inStream);
        MockMultipartHttpServletRequestBuilder file = MockMvcRequestBuilders.multipart("/file/upload").file(mfile);
    }

    /**
     * 执行请求并返回结果
     * @throws Exception
     */
    @Test
    public void mvcResult() throws Exception {
        MockMvc mvc = MockMvcBuilders.standaloneSetup(DemoController.class).build();
        // 创建 MockHttpServletRequestBuilder 类
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/");
        // 执行请求，并返回请求结果
        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        // 获取响应信息
        MockHttpServletResponse response = mvcResult.getResponse();
        // 获取请求状态码
        int status = response.getStatus();
        // 获取返回 @ResponseBody json字符串，进行反序列化处理即可
        // 注意：500/400/302 则是返回的 HTML 源码 String 类型
        String contentAsString = response.getContentAsString();
        // 若返回为模板（ModelAndView）
        ModelAndView modelAndView = mvcResult.getModelAndView();
        // 获取 view 名称
        Assert.notNull(modelAndView, "modelAndView 为 null");
        String viewName = modelAndView.getViewName();
        // 获取 model 中设置的参数
        Map<String, Object> modelMap = modelAndView.getModel();
        // 获取所有的参数及参数值信息
        modelMap.forEach((key, value) -> System.out.printf("key = %s, value = %s\n", key, value));
    }
}
