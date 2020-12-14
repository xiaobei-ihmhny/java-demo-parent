package com.xiaobei.mybatis.demo.spring;

import com.xiaobei.mybatis.demo.domain.User;
import com.xiaobei.mybatis.demo.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/13 22:14
 */
public class MyBatisSpringDemo {

    @Autowired
    private UserMapper userMapper;

    /**
     * 通过 JavaConfig 的方式来整合 Spring/MyBatis
     */
    @Test
    public void myBatisSpringWithJavaConfig() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MyBatisSpringDemo.class, MyBatisSpringConfig.class);
        applicationContext.refresh();
        // 获取相关的 Bean
        MyBatisSpringDemo myBatisSpringDemo = applicationContext.getBean(MyBatisSpringDemo.class);
        UserMapper userMapper = myBatisSpringDemo.userMapper;
        User user = userMapper.selectById(1L);
        System.out.println(user);
        applicationContext.close();
    }

    /**
     * 使用 xml 方式整合 Spring/MyBatis
     */
    @Test
    public void myBatisSpringWithXml() {
        String location = "META-INF/application.xml";
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(location);
        applicationContext.refresh();
        // 获取相关的 Bean
        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
        User user = userMapper.selectById(1L);
        System.out.println(user);
        applicationContext.close();
    }

    /**
     * 不能直接注入 {@link SqlSession} 的实现
     * {@link org.apache.ibatis.session.defaults.DefaultSqlSession}
     * 因为其不是线程安全的
     */
    @Test
    public void canUserDefaultSqlSession() {
        String location = "META-INF/application.xml";
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(location);
        applicationContext.refresh();
        // 获取相关的 Bean
        SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(1L);
        System.out.println(user);
        applicationContext.close();
    }
}
