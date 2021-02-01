package com.xiaobei.jdbc.demo.jdbctemplate;

import com.xiaobei.jdbc.demo.domain.User;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * TODO 学习 spring-jdbc 模块
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/10 15:33
 */
@Configuration
@PropertySource(value = "classpath:META-INF/jdbc.properties")
public class JdbcTemplateDemo {

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

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(JdbcTemplateDemo.class);
        applicationContext.refresh();
        JdbcTemplateDemo demo = applicationContext.getBean(JdbcTemplateDemo.class);
        // 进行相关的测试
        String sql = "SELECT * FROM user";
        JdbcTemplate jdbcTemplate = demo.jdbcTemplate;
        // TODO 需要深入学习 spring-jdbc
//        List<User> users = jdbcTemplate.queryForList(sql, User.class);
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setBirthPlace(rs.getString("birth_place"));
                user.setAge(rs.getInt("age"));
                return user;
            }
        });
        System.out.println(userList);
        applicationContext.close();
    }
}
