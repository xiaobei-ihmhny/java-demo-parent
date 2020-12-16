package com.xiaobei.mybatis.demo.commonmapper;

import com.xiaobei.mybatis.demo.domain.User;
import com.xiaobei.mybatis.demo.mapper.UserMapper;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-16 13:12:12
 */
public class MyBatisDemo {

    /**
     * 传统 Mybatis Spring 使用方式
     */
    @Test
    public void testOriginalMyBatis() {
        String location = "META-INF/commonmapper/application.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(location);
        applicationContext.refresh();
        UserMapper mapper = applicationContext.getBean(UserMapper.class);
        User user = mapper.selectById(1L);
        System.out.println(user);
        applicationContext.close();
    }

    @Test
    public void commonMapperWithXml() {
        String location = "META-INF/commonmapper/application-common-mapper.xml";
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext(location);
        com.xiaobei.mybatis.demo.commonmapper.mapper.UserMapper mapper =
                applicationContext.getBean(com.xiaobei.mybatis.demo.commonmapper.mapper.UserMapper.class);
        User user = new User();
        user.setId(1);
        User result = mapper.selectByPrimaryKey(user);
        System.out.println(result);
        List<User> users = mapper.selectAll();
        System.out.println(users);
    }
}