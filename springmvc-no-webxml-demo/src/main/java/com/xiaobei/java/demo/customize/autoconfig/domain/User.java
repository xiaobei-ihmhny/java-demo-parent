package com.xiaobei.java.demo.customize.autoconfig.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiaobei.java.demo.customize.autoconfig.databinder.BindParam;

//import javax.validation.constraints.Digits;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/25 14:40
 */
public class User implements Serializable {

    private static final long serialVersionUID = 8654039607172407191L;

    private Long id;

    @BindParam(name = "user_name")// 绑定请求参数
    @JsonProperty(value = "user_name")//绑定响应参数
//    @NotNull(message = "姓名不能为空")
    private String username;

//    @Max(value = 100)
    private Integer age;

    private Boolean sex;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Boolean getSex() {
        return sex;
    }

    public User setSex(Boolean sex) {
        this.sex = sex;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
