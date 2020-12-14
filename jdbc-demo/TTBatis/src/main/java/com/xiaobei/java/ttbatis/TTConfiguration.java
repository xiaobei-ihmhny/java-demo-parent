package com.xiaobei.java.ttbatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/14 21:15
 */
public class TTConfiguration {

    /**
     * sql -> Mapper 接口中返回值的 Class 类型
     */
    public static final Map<String, Class<?>> sqlToMapperReturnClassMap;

    /**
     * statementId -> sql
     */
    public static final Map<String, String> statementIdToSqlMap;

    public static final Map<String, Map<String, Class<?>>> statementIdToReturnTypeFieldNameToFieldTypeMap;

    static {
        ResourceBundle resource = ResourceBundle.getBundle("mappings");
        Set<String> keySet = resource.keySet();
        sqlToMapperReturnClassMap = new HashMap<>(keySet.size());
        statementIdToSqlMap = new HashMap<>(keySet.size());
        statementIdToReturnTypeFieldNameToFieldTypeMap = new HashMap<>(keySet.size());
        keySet.forEach(key -> {
            String value = resource.getString(key);
            statementIdToSqlMap.put(key, value);
            // 处理
            int methodDelimiterIndex = key.lastIndexOf(".");
            String clazz = key.substring(0, methodDelimiterIndex);
            String methodName = key.substring(methodDelimiterIndex + 1);
            try {
                Class<?> mapperClazz = Class.forName(clazz);
                for (Method method : mapperClazz.getMethods()) {
                    String name = method.getName();
                    if(name.equals(methodName)) {
                        // TODO 暂时不判断方法参数
                        Class<?> returnType = method.getReturnType();
                        sqlToMapperReturnClassMap.put(value, returnType);
                        Field[] declaredFields = returnType.getDeclaredFields();
                        for (Field field : declaredFields) {
                            String fieldName = field.getName();
                            Class<?> fieldType = field.getType();
                            Map<String, Class<?>> tempMap = statementIdToReturnTypeFieldNameToFieldTypeMap
                                    .computeIfAbsent(value, k -> new HashMap<>(declaredFields.length));
                            tempMap.put(fieldName, fieldType);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public <T> T getMapper(Class<T> clazz, TTSqlSession ttSqlSession) {
        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(), new Class[]{clazz},
                new TTMapperProxy(ttSqlSession));
    }
}
