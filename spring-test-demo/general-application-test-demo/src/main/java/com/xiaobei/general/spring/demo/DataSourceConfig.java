package com.xiaobei.general.spring.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 06:10
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource")
    @Profile("dev1")
    public DataSource dataSourceByDev1(
            @Value("${dev1.jdbc.url}") String url,
            @Value("${dev1.jdbc.username}") String username,
            @Value("${dev1.jdbc.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "dataSource")
    @Profile("test1")
    public DataSource dataSourceByTest1(
            @Value("${test1.jdbc.url}") String url,
            @Value("${test1.jdbc.username}") String username,
            @Value("${test1.jdbc.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
