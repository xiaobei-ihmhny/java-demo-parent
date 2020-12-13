package com.xiaobei.mybatis.demo;

import com.xiaobei.mybatis.demo.domain.User;
import com.xiaobei.mybatis.demo.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/10 16:30
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@TestPropertySource(value = "classpath:META-INF/jdbc.properties")
public class MybatisBasicTest {

    @org.springframework.context.annotation.Configuration
    static class MybatisConfig {

        @Bean(name = "sqlSessionFactory")
        public SqlSessionFactory sqlSessionFactory(@Value("classpath:META-INF/mybatis-config.xml") Resource resource) {
            SqlSessionFactory sqlSessionFactory = null;
            try {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(resource.getInputStream());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return sqlSessionFactory;
        }

    }


    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void test() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        sqlSession.getMapper()
    }

    @Test
    public void xmlByStatement() throws IOException {
        String resource = "META-INF/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // acquiring a SqlSession from SqlSessionFactory
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            User user = sqlSession.selectOne(
                    "com.xiaobei.mybatis.demo.mapper.UserMapper.selectById", 1L);
            System.out.println("直接使用 statement：" + user);
        }
    }

    @Test
    public void xmlByTypeSafe() throws IOException {
        String resource = "META-INF/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 1. 配置解析过程
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // type safe way
        // 2. 会话创建过程
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 3. 获取取 Mapper 对象
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            // 4. 执行 SQL 过程
            User user = mapper.selectById(1L);
            System.out.println("使用接口调用：" + user);
        }
    }

    @Test
    public void javaConfig() {
        DataSource dataSource = new DriverManagerDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
    }


    @Test
    public void testLambda() {
        Integer Yes=100;
        Integer No=null;
        System.out.println("-----------non-null----------");
        System.out.println("input:"+Yes+"\toutput:"+ Optional.ofNullable(Yes).orElse(invokeMethod("orElse")));
        System.out.println("input:"+Yes+"\toutput:"+Optional.ofNullable(Yes).orElseGet(() ->invokeMethod("orElseGet")));
        System.out.println("-----------null--------------");
        System.out.println("input:"+No+"\toutput:"+Optional.ofNullable(No).orElse(invokeMethod("orElse")));
        System.out.println("input:"+No+"\toutput:"+Optional.ofNullable(No).orElseGet(() ->invokeMethod("orElseGet")));

    }

    /**
     * invokeMethod
     * @param type
     * @return
     */
    public static Integer invokeMethod(String type){
        System.out.println("invokeType:"+type);
        return 9527;
    }
}
