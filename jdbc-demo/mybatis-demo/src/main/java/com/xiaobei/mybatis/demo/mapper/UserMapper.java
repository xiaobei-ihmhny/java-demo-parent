package com.xiaobei.mybatis.demo.mapper;

import com.mysql.cj.result.Row;
import com.xiaobei.mybatis.demo.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
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

    @Select("SELECT * FROM user WHERE username = #{username}")
    List<User> selectByUserName(@Param("username") String username);

    @SelectProvider(type = SqlProvider.class, method = "selectByParams")
    List<User> selectByParamsWithAnnotation(User user);

    /**
     * 定义相应的动态sql语句
     */
    class SqlProvider {

        /**
         * 根据给定的用户信息为条件查询满足条件的所有用户列表
         * @param user
         * @return
         */
        public static String selectByParams(User user) {
            return new SQL() {{
                SELECT("id", "username", "age", "birth_place");
                FROM("user");
                if(user != null && user.getId() != null) {
                    WHERE("id = #{id}");
                }
                if(user != null && user.getUsername() != null) {
                    WHERE("username LIKE CONCAT('%', #{username}, '%')");
                }
                if(user != null && user.getAge() != null) {
                    WHERE("age = #{age}");
                }
                if(user != null && user.getBirthPlace() != null) {
                    WHERE("birth_place = #{birthPlace}");
                }
            }}.toString();
        }
    }
}
