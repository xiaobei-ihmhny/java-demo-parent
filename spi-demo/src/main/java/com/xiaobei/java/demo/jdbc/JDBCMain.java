package com.xiaobei.java.demo.jdbc;

import java.sql.*;

/**
 * https://blog.csdn.net/ganchangshao/article/details/83615434
 * 早期的版本中
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-06-24 14:19:19
 */
public class JDBCMain {

    static String url = "jdbc:mysql://127.0.0.1:3306/test";

    static String username = "root";

    static String passport = "root";

    static String sql = "SELECT count(1) FROM `instance_module`";

    public static void main(String[] args) {
        // 1. 加载Mysql驱动类
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("找不到驱动程序类，加载驱动失败！");
//            e.printStackTrace();
//        }
        // 2. 创建数据库连接
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, passport);
        } catch (SQLException e) {
            System.out.println("数据库连接失败！");
            e.printStackTrace();
        }
        // 3. 创建一个preparedStatement
        try {
            Statement statement = con.createStatement();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 4. 执行sql语句

    }
}