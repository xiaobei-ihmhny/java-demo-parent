package com.xiaobei.mybatis.demo.plugins;

import com.xiaobei.mybatis.demo.domain.User;
import com.xiaobei.mybatis.demo.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/13 16:42
 */
public class RowBoundsTest {

    /**
     * 通过添加插件 {@link ResultSetPlugin} 可以知道使用 {@link RowBounds} 直接查询时，
     * 实际上是逻辑分页的。
     */
    @Test
    public void testSelectByRowBounds() {
        String location = "META-INF/mybatis-config-logical-paging.xml";
        rowBoundsWhetherToEnhance(location);
    }

    /**
     * 测试自定义插件 {@link ExecutorForQueryPlugin} ，
     * 实现修改原 {@link RowBounds} 的分页行为，改为物理分页
     */
    @Test
    public void testSelectByRowBoundsWithEnhanced() {
        String location = "META-INF/mybatis-config-physical-paging.xml";
        rowBoundsWhetherToEnhance(location);
    }

    private void rowBoundsWhetherToEnhance(String location) {
        SqlSessionFactory sqlSessionFactory;
        try (InputStream inputStream = Resources.getResourceAsStream(location)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                int start = 0;
                int pageSize = 1;
                RowBounds rowBounds = new RowBounds(start, pageSize);
                // 此时的查询将是逻辑分页，并非真正的分页
                List<User> users = mapper.selectByRowBounds(rowBounds);
                System.out.printf("users结果为：%s\n", users.size());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
