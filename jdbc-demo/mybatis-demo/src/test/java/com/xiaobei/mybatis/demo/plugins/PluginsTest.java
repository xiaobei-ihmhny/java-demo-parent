package com.xiaobei.mybatis.demo.plugins;

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
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/12 21:23
 */
public class PluginsTest {

    /**
     * @see ExamplePlugin 通过 plugin 做到，当更新的参数中含有 {@link User} 类型时，
     * 将其 {@link User#birthPlace} 属性强制修改为 “洛阳666”
     * @throws IOException
     */
    @Test
    public void pluginsTest() throws IOException {
        String resource = "META-INF/mybatis-config-with-plugins.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 1. 配置解析过程
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 2. 会话创建过程
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 3. 获取取 Mapper 对象
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            // 4. 执行 SQL 过程
            User newUser = new User();
            newUser.setId(1);
            newUser.setUsername("natie");
            newUser.setAge(2);
            newUser.setBirthPlace("洛阳");
            int updateCount = mapper.updateById(newUser);
            System.out.println("调用结果：" + updateCount);
        }
    }
}
