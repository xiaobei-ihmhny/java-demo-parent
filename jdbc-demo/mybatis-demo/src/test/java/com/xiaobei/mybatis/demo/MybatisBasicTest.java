package com.xiaobei.mybatis.demo;

import com.xiaobei.mybatis.demo.domain.User;
import com.xiaobei.mybatis.demo.mapper.UserMapper;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/10 16:30
 */
@DisplayName("mybatis基础使用示例")
public class MybatisBasicTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisBasicTest.class);

    @Nested
    @DisplayName("直接使用 statement 执行相关 sql 示例")
    class ExecutorByStatement {

        @Test
        @DisplayName("单个查询参数示例")
        void sampleStatement() {
            String resource = "META-INF/mybatis-config.xml";
            // 1. 配置解析过程
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactoryByXml(resource);
            // acquiring a SqlSession from SqlSessionFactory
            // 2. 会话创建过程
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                // 3. 查找相关 statement 并执行sql语句的过程
                User user = sqlSession.selectOne(
                        "com.xiaobei.mybatis.demo.mapper.UserMapper.selectById", 1L);
                LOGGER.info("直接使用 statement：{}", user);
            }
        }

        @Test
        @DisplayName("多个查询参数示例")
        void multipleQueryParams() {
            String resource =  "META-INF/mybatis-config.xml";
            // 1. 配置解析过程
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactoryByXml(resource);
            // 2. 由 SqlSessionFactory 对象获取 SqlSession 对象
            try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
                Map<String, Object> paramsMap = new HashMap<>(4);
                User user = new User();
                user.setId(1);
                user.setUsername("huihui");
                paramsMap.put("user", user);
                // 3. 执行相关的 sql 查询
                List<Object> list = sqlSession.selectList(
                        "com.xiaobei.mybatis.demo.mapper.UserMapper.selectByParams", paramsMap);
                LOGGER.info("查询到的用户信息为：{}", list);
            }
        }
    }

    @Nested
    @DisplayName("类型安全的执行sql语句")
    class ExecutorTypeSafe {

        @Test
        @DisplayName("通过接口间接调用 mapper.xml 中的 sql 语句")
        void sampleExecutorByInterface() {
            String resource = "META-INF/mybatis-config.xml";
            // 1. 配置解析过程
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactoryByXml(resource);
            // 2. 会话创建过程
            try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
                // 3. 获取 Mapper 代理对象
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                List<Integer> idList = new ArrayList<>();
                idList.add(1);
                idList.add(3);
                User user = new User();
                user.setUsername("i");
                // 4. 通过代理对象执行相关的sql语句
                List<User> users = userMapper.selectByParams(user, idList, RowBounds.DEFAULT);
                LOGGER.info("通过Mapper接口获取到的用户信息为：{}", users);
            } catch (Exception e) {
                LOGGER.error("执行sql语句的过程中发生异常 ", e);
            }
        }

        @Test
        @DisplayName("简单sql语句的注解使用示例")
        void executorByAnnotationSample() {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactoryByJavaConfig();
            try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                List<User> users = userMapper.selectByUserName("huihui");
                LOGGER.info("获取到的用户信息列表为：{}", users);
            } catch (Exception e) {
                LOGGER.error("SqlSession 执行过程出现异常，", e);
            }
        }

        @Test
        @DisplayName("复杂sql语句的注解使用示例")
        void executorByAnnotationComplex() {
            SqlSessionFactory sqlSessionFactory = getSqlSessionFactoryByJavaConfig();
            try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                User user = new User();
                user.setUsername("i");
                List<User> users = mapper.selectByParamsWithAnnotation(user);
                LOGGER.info("获取到的用户信息列表为：{}", users);
            } catch (Exception e) {
                LOGGER.error("sql执行过程出现异常 ", e);
            }
        }
    }

    /**
     * 解析 xml 配置来获取 {@link SqlSessionFactory} 对象
     * @param resource
     * @return
     */
    private SqlSessionFactory getSqlSessionFactoryByXml(String resource) {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 基于 java 代码的方式配置 {@link SqlSessionFactory}
     * @return
     */
    private SqlSessionFactory getSqlSessionFactoryByJavaConfig() {
        String jdbcProperties = "META-INF/jdbc.properties";
        String typeAliasesPackage = "com.xiaobei.mybatis.demo.domain";
        String mapperResource = "META-INF/mapper/UserMapper.xml";
        Configuration configuration = new Configuration();
        try {
            Properties properties = Resources.getResourceAsProperties(jdbcProperties);
            // 配置 Environment
            populateEnvironment(configuration, properties);
            // 开启下划线转驼峰配置
            configuration.setMapUnderscoreToCamelCase(Boolean.TRUE);
            // 别名配置
            configuration.getTypeAliasRegistry().registerAliases(typeAliasesPackage);
            // 添加 mapper.xml 资源路径
            InputStream inputStream = Resources.getResourceAsStream(mapperResource);
            XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(
                    inputStream, configuration, mapperResource, configuration.getSqlFragments());
            mapperBuilder.parse();
        } catch (IOException e) {
            LOGGER.error("配置 SqlSessionFactory 时出现异常，", e);
        }
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private void populateEnvironment(Configuration configuration, Properties properties) {
        DataSourceFactory dataSourceFactory = new PooledDataSourceFactory();
        Properties props = new Properties();
        props.put("driver", properties.get("jdbc.driver"));
        props.put("username", properties.get("jdbc.username"));
        props.put("password", properties.get("jdbc.password"));
        props.put("url", properties.get("jdbc.url"));
        dataSourceFactory.setProperties(props);
        DataSource dataSource = dataSourceFactory.getDataSource();
        TransactionFactory factory = new JdbcTransactionFactory();
        Environment environment = new Environment("dev", factory, dataSource);
        configuration.setEnvironment(environment);
    }

}
