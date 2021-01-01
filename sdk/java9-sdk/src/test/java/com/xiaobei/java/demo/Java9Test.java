package com.xiaobei.java.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-31 09:57:57
 */
public class Java9Test {

    int state = 0;

    @Test
    void bitTest() {
//        System.out.println(state |= 1 << 0);
//        System.out.println(state |= 1 << 1);
//        System.out.println(state |= 1 << 2);
//        System.out.println(state |= 1 << 3);
        System.out.println(state |= 1 << 4);
    }

    int a;

    User user;

    @BeforeEach
    void beforeEach() {
        a = 1;
        user = new User().setName("huihui");
    }

    @Test
    void varTest() {
        int b;
        b = a;
        a = 0;
        System.out.println(b);
        User u;
        u = user;
        user = null;
        System.out.println(user);
        System.out.println(u);
    }

    static class User {
        private String name;

        public String getName() {
            return name;
        }

        public User setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}