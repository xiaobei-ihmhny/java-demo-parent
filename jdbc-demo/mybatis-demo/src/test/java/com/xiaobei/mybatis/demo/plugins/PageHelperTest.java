package com.xiaobei.mybatis.demo.plugins;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaobei.mybatis.demo.domain.User;
import com.xiaobei.mybatis.demo.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/13 06:44
 */
public class PageHelperTest {

    @Test
    public void pageHelper() throws IOException {
        String resource = "META-INF/mybatis-config-with-pagehelper.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 1. 配置解析过程
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // type safe way
        // 2. 会话创建过程
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 3. 获取取 Mapper 对象
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            // 4. 执行 SQL 过程
            User user = new User();
            user.setUsername("e");
//            PageInfo<User> userPage = PageHelper.startPage(1,2)
//                    .doSelectPageInfo(() -> mapper.selectByParams(user));
//            List<User> list = userPage.getList();
            PageHelper.startPage(1, 2);
//            ReflectionTestUtils.invokeMethod(PageHelper.class, "setLocalPage", new Page<User>(1,2));
            List<User> users = mapper.selectByParams(user, null, null);
            PageInfo<User> userPageInfo = new PageInfo<>(users);
            System.out.println("使用接口调用：" + users);
            System.out.println("使用接口调用：" + userPageInfo);
        }
    }
}
