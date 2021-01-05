package com.xiaobei.java.demo.features.collection;

import com.xiaobei.java.demo.features.domain.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Stream 测试类的公共部分
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-05 14:45:45
 */
public interface StreamTest {

    /**
     * 获取 person 列表
     * @return
     */
    static List<Person> personList() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Tom", 8900, 11, "male", "昌平"));
        personList.add(new Person("Jack", 7000, 12, "male", "昌平"));
        personList.add(new Person("Lily", 7800, 13, "female", "朝阳"));
        personList.add(new Person("Anni", 8200, 14, "female", "海淀"));
        personList.add(new Person("Owen", 9500, 12, "male", "通州"));
        personList.add(new Person("Alisa", 7900, 11, "female", "海淀"));
        return personList;
    }
}
