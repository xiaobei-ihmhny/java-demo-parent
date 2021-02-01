package com.xiaobei.jdbc.demo.domain;

import com.xiaobei.jdbc.demo.dbutils.ColumnName;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/10 10:21
 */
public class User {

    private Integer id;

    private String username;

    private Integer age;

    @ColumnName(value = "birth_place")
    private String birthPlace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    @ColumnName(value = "birth_place")
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age='" + age + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                '}';
    }
}
