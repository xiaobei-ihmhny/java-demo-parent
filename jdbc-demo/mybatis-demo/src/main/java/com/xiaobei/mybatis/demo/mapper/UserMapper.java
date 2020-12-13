package com.xiaobei.mybatis.demo.mapper;

import com.mysql.cj.result.Row;
import com.xiaobei.mybatis.demo.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/11 10:27
 */
public interface UserMapper {

    User selectById(Long id);

    int updateById(User user);

    List<User> selectByParams(@Param("user") User user, @Param("list") List<Integer> idList, RowBounds rowBounds);

    List<User> selectByRowBounds(RowBounds rowBounds);
}
