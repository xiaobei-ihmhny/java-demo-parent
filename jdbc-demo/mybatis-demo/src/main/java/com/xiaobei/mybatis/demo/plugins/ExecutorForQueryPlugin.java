package com.xiaobei.mybatis.demo.plugins;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.javassist.tools.reflect.Reflection;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.test.util.AopTestUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/13 17:03
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
public class ExecutorForQueryPlugin implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorForQueryPlugin.class);

    private boolean hasNestedResultMaps;

    @Override
    @SuppressWarnings("rawtypes")
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        String appendSql = "";
        if(rowBounds != null) {
            int offset = rowBounds.getOffset();
            int limit = rowBounds.getLimit();
            appendSql = " limit " + offset + " , " + limit;
        }
        Executor executor = (Executor) invocation.getTarget();
        BoundSql boundSql = getBoundSql(mappedStatement, parameterObject, appendSql);
        CacheKey key = executor.createCacheKey(mappedStatement, parameterObject, RowBounds.DEFAULT, boundSql);
        // 如何将 BoundSql 中的 sql 更新？？
        return executor.query(mappedStatement, parameterObject, RowBounds.DEFAULT, resultHandler, key, boundSql);
    }

    public BoundSql getBoundSql(MappedStatement mappedStatement, Object parameterObject, String needAppendSql) {
        SqlSource sqlSource = mappedStatement.getSqlSource();
        Configuration configuration = mappedStatement.getConfiguration();
        ParameterMap parameterMap = mappedStatement.getParameterMap();
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings == null || parameterMappings.isEmpty()) {
            boundSql = new BoundSql(configuration, boundSql.getSql() + needAppendSql, parameterMap.getParameterMappings(), parameterObject);
        }

        // check for nested result maps in parameter mappings (issue #30)
        for (ParameterMapping pm : boundSql.getParameterMappings()) {
            String rmId = pm.getResultMapId();
            if (rmId != null) {
                ResultMap rm = configuration.getResultMap(rmId);
                if (rm != null) {
                    hasNestedResultMaps |= rm.hasNestedResultMaps();
                }
            }
        }
        // 将 hasNestedResultMaps 的结果设置到 MappedStatement 中
        setField(mappedStatement, MappedStatement.class, "hasNestedResultMaps", hasNestedResultMaps, boolean.class);
        return boundSql;
    }

    public void setField(@Nullable Object targetObject, @Nullable Class<?> targetClass,
                                @Nullable String name, @Nullable Object value, @Nullable Class<?> type) {

        Assert.isTrue(targetObject != null || targetClass != null,
                "Either targetObject or targetClass for the field must be specified");

        if (targetClass == null) {
            targetClass = targetObject.getClass();
        }

        Field field = ReflectionUtils.findField(targetClass, name, type);
        if (field == null) {
            throw new IllegalArgumentException(String.format(
                    "Could not find field '%s' of type [%s] on %s or target class [%s]", name, type,
                    safeToString(targetObject), targetClass));
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format(
                    "Setting field '%s' of type [%s] on %s or target class [%s] to value [%s]", name, type,
                    safeToString(targetObject), targetClass, value));
        }
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, targetObject, value);
    }


    private static String safeToString(@Nullable Object target) {
        try {
            return String.format("target object [%s]", target);
        }
        catch (Exception ex) {
            return String.format("target of type [%s] whose toString() method threw [%s]",
                    (target != null ? target.getClass().getName() : "unknown"), ex);
        }
    }
}
