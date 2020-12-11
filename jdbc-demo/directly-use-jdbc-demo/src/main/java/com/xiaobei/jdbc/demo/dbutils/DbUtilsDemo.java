package com.xiaobei.jdbc.demo.dbutils;

import com.xiaobei.jdbc.demo.domain.User;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/10 09:10
 */
@Configuration
@PropertySource(value = "classpath:META-INF/jdbc.properties")
public class DbUtilsDemo {

    @Bean(name = "dataSource")
    public DataSource dataSource(@Value("${jdbc.url}") String url,
                                 @Value("${jdbc.username}") String username,
                                 @Value("${jdbc.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DbUtilsDemo.class);
        applicationContext.refresh();
        DbUtilsDemo demo = applicationContext.getBean(DbUtilsDemo.class);
        demo.demo1();
        demo.demo2();
        demo.demo3();
        applicationContext.close();
    }

    /**
     * 数据库字段与pojo完全一致
     * @throws SQLException
     */
    public void demo1() throws SQLException {
        String sql = "SELECT * FROM user";
        QueryRunner runner = new QueryRunner(dataSource);
        // pojo 字段与数据库字段完全一样时，可直接用以下查询
        final List<User> userList = runner.query(sql, new BeanListHandler<>(User.class));
        System.out.println(userList);
    }

    public void demo2() throws SQLException {
        String sql = "SELECT * FROM user";
        QueryRunner runner = new QueryRunner(dataSource);
        final List<User> userList = runner.query(sql, userResultSetHandler());
        System.out.println(userList);
    }

    public void demo3() throws SQLException {
        String sql = "SELECT * FROM user";
        BasicRowProcessor convert = new BasicRowProcessor(new AnnotationBeanProcessor());
        QueryRunner runner = new QueryRunner(dataSource);
        List<User> userList = runner.query(sql, new BeanListHandler<>(User.class, convert));
        System.out.println(userList);
    }

    /**
     * 定义 user 对象结果的处理逻辑
     * @return
     */
    public ResultSetHandler<List<User>> userResultSetHandler() {
        return rs -> {
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                final int id = rs.getInt("id");
                final String username = rs.getString("username");
                final String birthPlace = rs.getString("birth_place");
                final int age = rs.getInt("age");
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setAge(age);
                user.setBirthPlace(birthPlace);
                userList.add(user);
            }
            return userList;
        };
    }


}
