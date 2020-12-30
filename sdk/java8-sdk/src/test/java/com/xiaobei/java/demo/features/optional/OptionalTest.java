package com.xiaobei.java.demo.features.optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Java的Optional却根本无法序列化。为什么java8的Optional没有实现序列化，
 * 这里有个讨论，可以看看http://mail.openjdk.java.net/pipermail/jdk8-dev/2013-September/003186.html</p>
 * <p></p>
 * <p>另外Java8比Guava多了ifPresent、map、 filter、 flatMap、 orElseThrow这些方法。
 * 鉴于现在使用Guava Optional的人越来越少，不提也罢。</p>
 * <p></p>
 * <p>Optional会对GC有一定压力，如果开发底层框架，还是慎重使用，
 * netty就曾经过测试，最后放弃了Optional。</p>
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-30 09:37:37
 */
public class OptionalTest {

    Map<String, String> params;

    Customer customer;

    @BeforeEach
    void init() {
        System.out.println("初始化 map");
    }

    @Test
    public void simpleWithoutLambda() {
        Map<String, String> map = params;
        if(map == null) {
            map = new HashMap<>();
        }
        System.out.println(map);
    }

    /**
     * 使用 {@link Optional#ofNullable(Object)} 方法来避免出现过多的 {@code if} 判断。
     */
    @Test
    public void simpleWithLambda() {
        Map<String, String> map = params;
        Map<String, String> notNullMap = Optional.ofNullable(map).orElse(new HashMap<>(4));
        System.out.println(notNullMap);
    }

    @Test
    void multiNestedClassWithoutLambda() {
        String code = "100001";
        if(customer != null) {
            Address address = customer.getAddress();
            if(address != null) {
                City city = address.getCity();
                if(city != null) {
                    String cityCode = city.getCityCode();
                    if(cityCode != null && cityCode.length() == 6) {
                        code = cityCode;
                    }
                }
            }
        }
        System.out.println(code);
    }


    @Test
    void multiNestedClassWithLambda() {
        String code = "100001";
        String resultCode = Optional.ofNullable(customer)
                .map(Customer::getAddress)
                .map(Address::getCity)
                .map(City::getCityCode)
                .filter(cityCode -> cityCode.length() == 6)
                .orElse(code);
        System.out.println(resultCode);
    }


    static class City {
        private String cityCode;

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("City{");
            sb.append("cityCode='").append(cityCode).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    static class Address {
        private City city;

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Address{");
            sb.append("city=").append(city);
            sb.append('}');
            return sb.toString();
        }
    }

    static class Customer {
        private Address address;

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Customer{");
            sb.append("address=").append(address);
            sb.append('}');
            return sb.toString();
        }
    }

}