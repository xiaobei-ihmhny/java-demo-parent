package com.xiaobei.general.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/05 06:48
 */
@Service
public class UserService {

    @Autowired
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    private String privateMethod(Long methodId) {
        return "这是一个私有方法，方法id为：" + methodId;
    }
}
