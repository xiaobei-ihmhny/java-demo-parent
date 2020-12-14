package com.xiaobei.java.ttbatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/14 21:18
 */
public class TTMapperProxy implements InvocationHandler {

    private TTSqlSession ttSqlSession;

    public TTMapperProxy(TTSqlSession ttSqlSession) {
        this.ttSqlSession = ttSqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String statementId = method.getDeclaringClass().getName() + "." + method.getName();
        return ttSqlSession.selectOne(statementId, args[0]);
    }
}
