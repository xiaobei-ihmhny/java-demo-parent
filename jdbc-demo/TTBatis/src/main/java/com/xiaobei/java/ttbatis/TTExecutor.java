package com.xiaobei.java.ttbatis;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/14 21:16
 */
public class TTExecutor {

    @SuppressWarnings("unchecked")
    public <T> T query(String sql, Object parameter) {
        // TODO 直接使用JDBC的查询方式
        String url = "jdbc:mysql://127.0.0.1:3306/db1?serverTimezone=GMT";
        String username = "root";
        String password = "root";
        User user = null;
        // 从 TTConfiguration 中获取 sql 对应的 返回值类型
        Class<?> returnType = TTConfiguration.sqlToMapperReturnClassMap.get(sql);
        T resultType = null;
        try {
            resultType = (T) returnType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Map<String, Class<?>> returnTypeFieldNameToFieldTypeMap =
                TTConfiguration.statementIdToReturnTypeFieldNameToFieldTypeMap.get(sql);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(String.format(sql, parameter))) {
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (resultSet.next()) {
                    if(resultType == null) {
                        return null;
                    }
                    for (int i = 1; i <= columnCount; i++) {
                        String columnLabel = metaData.getColumnLabel(i);
                        Class<?> aClass = returnTypeFieldNameToFieldTypeMap.get(columnLabel);
                        if (aClass != null) {
                            Object currentPropertyValue = resultSet.getObject(i, aClass);
                            Field field = resultType.getClass().getDeclaredField(columnLabel);
                            field.setAccessible(true);
                            field.set(resultType, currentPropertyValue);
                        }
                    }
                }
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        }
        return resultType;
    }
}
