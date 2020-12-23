package com.xiaobei.mybatis.demo.domain;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/11 10:35
 */
public class User implements Serializable {

    @Id
    private Integer id;

    private String username;

    private Integer age;

    private String birthPlace;

    /**
     * 使用 {@link Transient} 来标注表中没有的额外字段
     */
    @Transient
    private String name;

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

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", birthPlace='" + birthPlace + '\'' +
                '}';
    }
}
