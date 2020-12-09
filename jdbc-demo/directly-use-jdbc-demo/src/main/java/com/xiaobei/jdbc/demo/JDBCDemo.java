package com.xiaobei.jdbc.demo;

import java.sql.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/09 23:19
 */
public class JDBCDemo {

    /**
     * 最原始的操作 jdbc 的方式
     * @param args
     */
    public static void main(String[] args) {
        String sql = "SELECT * FROM user";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql:///db1?serverTimezone=GMT", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                final int id = resultSet.getInt("id");
                final String username = resultSet.getString("username");
                final int age = resultSet.getInt("age");
                System.out.printf("id = %d, username = %s, age = %d\n", id, username, age);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
