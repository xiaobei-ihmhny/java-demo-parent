package com.xiaobei.mybatis.demo.plugins;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/13 16:34
 */
@Intercepts({@Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class})})
public class ResultSetPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Statement statement = (Statement) args[0];
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            System.out.println(username);
        }
        resultSet.beforeFirst();
        return invocation.proceed();
    }
}
