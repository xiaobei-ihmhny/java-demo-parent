package com.xiaobei.java;

import com.xiaobei.java.ttbatis.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/14 21:28
 */
public class TTBatisDemo {

    public static void main(String[] args) {
        TTConfiguration ttConfiguration = new TTConfiguration();
        TTExecutor ttExecutor = new TTExecutor();
        TTSqlSession ttSqlSession = new TTSqlSession(ttConfiguration, ttExecutor);
        UserMapper mapper = ttSqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(1);
        System.out.println(user);
    }
}
