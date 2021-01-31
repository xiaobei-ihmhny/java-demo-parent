package com.xiaobei.mybatis.demo.cache;

import com.xiaobei.mybatis.demo.domain.User;
import com.xiaobei.mybatis.demo.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 测试二级缓存
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/12 07:35
 */
public class SecondLevelCacheTest {

    /**
     * 全局开启二级缓存，并且对应的 sql 没有进行个性化配置
     */
    @Test
    public void secondLevelDefaultConfig() {
        String location = "META-INF/mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory;
        try (InputStream inputStream = Resources.getResourceAsStream(location)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try (SqlSession sqlSession1 = sqlSessionFactory.openSession();
                 SqlSession sqlSession2 = sqlSessionFactory.openSession()) {
                UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
                UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
                User user1 = mapper1.selectById(1L);
                // 一定要执行 commit() 操作，否则无法将相关结果添加到缓存中，
                // 也就无法实现跨 session 共用了。
                sqlSession1.commit();
                System.out.println("第一次查询：" + user1);
                User user2 = mapper2.selectById(1L);
                System.out.println("第二次查询：" + user2);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * TODO 测试 statements 覆盖全局配置时的缓存使用情况
     */
    @Test
    public void secondLevelWithStatementsUserCustomCache() {

    }

    @Test
    public void secondLevelWithUpdateOperate() {
        String location = "META-INF/mybatis-config.xml";
        SqlSessionFactory sqlSessionFactory;
        try (InputStream inputStream = Resources.getResourceAsStream(location)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try (SqlSession sqlSession1 = sqlSessionFactory.openSession();
                 SqlSession sqlSession2 = sqlSessionFactory.openSession();
                 SqlSession sqlSession3 = sqlSessionFactory.openSession()) {
                UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
                // session1 调用了查询操作并提交了事务（将相关数据写入了缓存）
                User user1 = mapper1.selectById(1L);
                sqlSession1.commit();
                System.out.println("第一次查询：" + user1);
                // session2 执行修改操作，并提交了事务
                UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
                User newUser = new User();
                newUser.setId(1);
                newUser.setUsername("natie");
                newUser.setAge(2);
                newUser.setBirthPlace("洛阳");
                int updateCount = mapper2.updateById(newUser);
                sqlSession2.commit();
                System.out.println("影响条数：" + updateCount);
                UserMapper mapper3 = sqlSession3.getMapper(UserMapper.class);
                // session3 又调用了查询操作，此时可以从缓存中获取 session 的查询结果吗？会重新查询，因为缓存已刷新！
                User user3 = mapper3.selectById(1L);
                System.out.println("第二次查询：" + user3);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
