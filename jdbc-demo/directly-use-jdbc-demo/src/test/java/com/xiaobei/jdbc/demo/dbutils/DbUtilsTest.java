package com.xiaobei.jdbc.demo.dbutils;

import com.xiaobei.jdbc.demo.domain.User;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DbUtils示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-01 07:43:43
 */
@DisplayName("DbUtils使用示例")
public class DbUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbUtilsTest.class);

    /**
     * 数据库表字段与javaBean一一对应
     * @throws SQLException
     */
    @Test
    void sampleQuery() throws SQLException {
        // 获取 DataSource
        HikariDataSource dataSource = getHikariDataSource();
        QueryRunner runner = new QueryRunner(dataSource);
        ResultSetHandler<List<User>> handler = new BeanListHandler<>(User.class);
        List<User> users = runner.query("SELECT * FROM user", handler);
        LOGGER.info("查询到的用户信息为：{}", users);
    }

    /**
     * 通过实现 {@link ResultSetHandler} 来自定义数据库字段与javaBean之间的对应关系
     * @throws SQLException
     */
    @Test
    @DisplayName("自定义映射关系")
    void customMapping() throws SQLException {
        // 1. 获取数据库连接池
        try (HikariDataSource hikariDataSource = getHikariDataSource()) {
            QueryRunner runner = new QueryRunner(hikariDataSource);
            List<User> userList = runner.query("SELECT * FROM user", rs -> {
                List<User> list = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    int age = rs.getInt("age");
                    String birthPlace = rs.getString("birth_place");
                    User user = new User();
                    user.setId(id);
                    user.setUsername(username);
                    user.setAge(age);
                    user.setBirthPlace(birthPlace);
                    list.add(user);
                }
                return list;
            });
            LOGGER.info("查询出的用户信息为：{}", userList);
        }
    }

    @Test
    @DisplayName("通过自定义注解来自定义数据库字段与javaBean字段的对应关系")
    void customMappingWithAnnotation() {
        try (HikariDataSource hikariDataSource = getHikariDataSource()) {
            QueryRunner runner = new QueryRunner(hikariDataSource);
            BasicRowProcessor convert = new BasicRowProcessor(new AnnotationBeanProcessor2());
            BeanListHandler<User> handler = new BeanListHandler<>(User.class, convert);
            List<User> userList = runner.query("SELECT * FROM user", handler);
            LOGGER.info("获取自定义注解的查询结果：{}", userList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * {@link BeanProcessor#columnToPropertyOverrides} 来指定特殊的匹配关系
     */
    @Test
    @DisplayName("通过columnToPropertyOverrides来指定特殊的匹配关系")
    void customMappingWithColumnToPropertyOverrides() {
        try(HikariDataSource hikariDataSource = getHikariDataSource()) {
            QueryRunner runner = new QueryRunner(hikariDataSource);
            Map<String, String> columnToPropertyOverrides = new HashMap<>(2);
            columnToPropertyOverrides.put("birth_place", "birthPlace");
            BasicRowProcessor convert = new BasicRowProcessor(new BeanProcessor(columnToPropertyOverrides));
            BeanListHandler<User> handler = new BeanListHandler<>(User.class, convert);
            List<User> userList = runner.query("SELECT * FROM user", handler);
            LOGGER.info("获取用户列表信息：{}", userList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 获取 Hikari 连接池
     * @return
     */
    private HikariDataSource getHikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mybatis?serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }
}
