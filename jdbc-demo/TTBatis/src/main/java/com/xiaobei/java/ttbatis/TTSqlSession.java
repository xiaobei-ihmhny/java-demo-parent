package com.xiaobei.java.ttbatis;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/14 21:13
 */
public class TTSqlSession {

    private TTConfiguration ttConfiguration;// 配置类

    private TTExecutor ttExecutor;// 执行器

    public TTSqlSession(TTConfiguration ttConfiguration, TTExecutor ttExecutor) {
        this.ttConfiguration = ttConfiguration;
        this.ttExecutor = ttExecutor;
    }

    public <T> T getMapper(Class<T> clazz) {
        return ttConfiguration.getMapper(clazz, this);
    }

    public <T> T selectOne(String statementId, Object parameter) {
        // 根据statementId获取需要执行的sql语句
        String sql = TTConfiguration.statementIdToSqlMap.get(statementId);
        return ttExecutor.query(sql, parameter);
    }

}
