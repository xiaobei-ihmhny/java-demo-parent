package com.xiaobei.mybatis.demo.spring;

import com.xiaobei.mybatis.demo.mapper.UserMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/13 22:09
 */
@Configuration
@PropertySource(value = "classpath:META-INF/jdbc.properties")
public class MyBatisSpringConfig {

    @Bean(name = "dataSource")
    public DataSource dataSource(@Value("${jdbc.url}") String url,
                                 @Value("${jdbc.username}") String username,
                                 @Value("${jdbc.password}") String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Value("classpath:META-INF/mybatis-config.xml") Resource resource,
            DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(resource);
        return factoryBean.getObject();
    }

    @Bean(name = "userMapper")
    public UserMapper userMapper(SqlSessionFactory sqlSessionFactory) throws Exception {
        MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<>();
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        factoryBean.setMapperInterface(UserMapper.class);
        return factoryBean.getObject();
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
//        return template.getMapper(UserMapper.class);
    }
}
