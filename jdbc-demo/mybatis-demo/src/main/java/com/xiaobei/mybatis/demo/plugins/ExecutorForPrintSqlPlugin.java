package com.xiaobei.mybatis.demo.plugins;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 打印出实际执行的sql
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/13 18:25
 */
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
        ),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
})
public class ExecutorForPrintSqlPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        // 获取原始 sql
        String sql = getOriginalSql(boundSql.getSql());
        // 将相关参数放入对应位置
        String finallySql = getFinallySql(sql, boundSql);
        System.out.printf("完整sql语句为：%s\n", finallySql);
        long startTime = System.currentTimeMillis();
        Object result = invocation.proceed();
        System.out.printf("执行耗时：%d ms\n", System.currentTimeMillis() - startTime);
        return result;
    }

    private String getFinallySql(String sql, BoundSql boundSql) {
        List<ParameterMapping> mappings = boundSql.getParameterMappings();
        for (ParameterMapping mapping : mappings) {
            String property = mapping.getProperty();
            Class<?> javaType = mapping.getJavaType();
            Object param = boundSql.getAdditionalParameter(property);
            if(String.class.equals(javaType)) {
                sql = sql.replaceFirst("\\?", "'" + param.toString() +"'");
            } else {
                sql = sql.replaceFirst("\\?", param.toString());
            }
        }
        return sql;
    }

    private List<Object> parameterList(BoundSql boundSql) {
        List<Object> parameterList = new ArrayList<>();
        List<ParameterMapping> mappings = boundSql.getParameterMappings();
        mappings.stream()
                .map(ParameterMapping::getProperty)
                .forEach(propertyName -> {
                    Object parameter = boundSql.getAdditionalParameter(propertyName);
                    parameterList.add(parameter);
                });
        return parameterList;
    }

    private String getOriginalSql(String sql) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(sql.getBytes(StandardCharsets.UTF_8)),
                        StandardCharsets.UTF_8));
        String line;
        StringBuffer strbuf=new StringBuffer();
        while ( (line = br.readLine()) != null ) {
            if(!line.trim().equals("")) {
                strbuf.append(line.trim()).append(" ");
            }
        }
        return strbuf.toString();
    }
}
