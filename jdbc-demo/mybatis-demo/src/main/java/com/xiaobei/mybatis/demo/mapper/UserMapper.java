package com.xiaobei.mybatis.demo.mapper;

import com.xiaobei.mybatis.demo.domain.User;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/11 10:27
 */
public interface UserMapper {

    User selectById(Long id);

    int updateById(User user);
}
