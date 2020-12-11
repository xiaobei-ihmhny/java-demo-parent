package com.xiaobei.mybatis.demo.cache;

import com.xiaobei.mybatis.demo.domain.User;
import com.xiaobei.mybatis.demo.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 测试 MyBatis 一级缓存，测试环境需要关闭二级缓存，
 * 即设置参数 localCacheScope 为 默认的 SESSION
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/11 22:49
 */
public class FirstLevelCacheTest {

    /**
     * 相同的会话（SqlSession）之间可以共享缓存
     * 不同的传话（SqlSession）之间不能共享缓存
     */
    @Test
    public void sameAndDifferentSqlSessionFirstCacheUsage() {
        String resource = "META-INF/mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory;
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try (SqlSession sqlSession1 = sqlSessionFactory.openSession();
                 SqlSession sqlSession2 = sqlSessionFactory.openSession()) {
                UserMapper mapper11 = sqlSession1.getMapper(UserMapper.class);
                UserMapper mapper12 = sqlSession1.getMapper(UserMapper.class);
                UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
                // 使用 sqlSession1 进行多次查询，其中 user12 的结果会直接使用 user11的查询结果
                User user11 = mapper11.selectById(1L);
                System.out.println(user11);
                User user12 = mapper12.selectById(1L);
                System.out.println(user12);
                // 使用 sqlSession2 进行的查询，无法再使用 sqlSession1 的缓存
                User user = mapper2.selectById(1L);
                System.out.println(user);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 默认情况下，一个会话中发生了 DML 语句时，一级缓存会自动失效
     * TODO 如何演示同一个传话中发生 DML 语句时，一级缓存不会失效呢？
     */
    @Test
    public void firstCacheInvalidWhileDMLHappen() {
        String resource = "META-INF/mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory;
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                User user = mapper.selectById(1L);
                System.out.println("第一次查询的结果：" + user);
                // 执行修改操作
                User newUser = new User();
                newUser.setId(1);
                newUser.setUsername("natie");
                newUser.setAge(2);
                newUser.setBirthPlace("洛阳");
                int updateCount = mapper.updateById(newUser);
                System.out.println("更新影响条数：" + updateCount);
                // 再次执行查询语句
                User ageUser = mapper.selectById(1L);
                System.out.println("更新后的查询结果：" + ageUser);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 不同会话一个会话中更新了数据，另一个会话中可以会出现“脏数据”
     */
    @Test
    public void firstCacheSharedAcrossSessionHappenDirtyData() {
        String resource = "META-INF/mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory;
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try (SqlSession sqlSession1 = sqlSessionFactory.openSession();
                 SqlSession sqlSession2 = sqlSessionFactory.openSession()) {
                UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
                UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
                User user1 = mapper1.selectById(1L);
                System.out.println("使用会话1查询数据：" + user1);
                // 使用 sqlSession2 更新数据
                // 执行修改操作
                User newUser = new User();
                newUser.setId(1);
                newUser.setUsername("natie");
                newUser.setAge(2);
                newUser.setBirthPlace("洛阳");
                int updateCount = mapper2.updateById(newUser);
                sqlSession2.commit();
                System.out.println("更新影响条数：" + updateCount);
                User user = mapper1.selectById(1L);
                System.out.println("使用会话1再次查询数据：" + user);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
