package com.xiaobei.mybatis.demo.plugins;

import com.xiaobei.mybatis.demo.domain.User;
import com.xiaobei.mybatis.demo.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/13 16:42
 */
public class PrintSqlTest {

    /**
     * 通过添加插件 {@link ExecutorForPrintSqlPlugin} 打印实际执行的sql语句。
     * 配置：<setting name="logImpl" value="NO_LOGGING"/> 关闭系统日志
     */
    @Test
    public void testSelectByRowBounds() {
        String location = "META-INF/mybatis-config-printsql.xml";
        SqlSessionFactory sqlSessionFactory;
        try (InputStream inputStream = Resources.getResourceAsStream(location)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                // 4. 执行 SQL 过程
                User user = new User();
                user.setUsername("i");
                user.setBirthPlace("洛阳");
                List<Integer> idList = new ArrayList<>();
                idList.add(1);
                idList.add(3);
                List<User> userList = mapper.selectByParams(user, null, new RowBounds(0, 1));
                System.out.printf("userList结果为：%s\n", userList);
                List<User> users = mapper.selectByParams(user, idList, null);
                System.out.printf("users结果为：%s\n", users);

            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}
