package com.xiaobei.java.demo.features.collection;

import com.xiaobei.java.demo.features.domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-05 14:50:50
 */
public class StreamForeachFindMatchTest {

    /**
     * 3.1 遍历/匹配（foreach/find/match）
     */
    @Test
    void foreachFindMatch() {
        // 遍历输出所有元素
        StreamTest.personList().forEach(System.out::println);
        // 匹配第一个元素
        Optional<Person> firstPerson = StreamTest.personList().stream().filter(p -> p.getAge() > 18).findFirst();
        firstPerson.ifPresent(p -> System.out.printf("匹配的第一个元素为：%s%n", p));
        // 匹配任何一个
        Optional<Person> findPerson = StreamTest.personList().parallelStream().filter(p -> "male".equals(p.getSex())).findAny();
        findPerson.ifPresent(p -> System.out.printf("匹配的一个元素为：%s%n", p));
        // 是否存在符合条件的元素
        boolean anyMatch = StreamTest.personList().stream().anyMatch(p -> p.getSalary() > 9000);
        System.out.printf("匹配的第一个元素为：%s%n", anyMatch);
    }

    /**
     * 3.2 筛选（filter）
     */
    @Test
    void filter() {
        // 筛选员工中工资高于8000的人，并形成新的集合。
        List<Person> result =
                StreamTest.personList().stream()
                        .filter(p -> p.getSalary() > 8000)
                        .collect(Collectors.toList());
        // 打印筛选中的集合
        result.forEach(System.out::println);
        System.out.println("=======================");

        // 筛选员工中工资高于性别为 male 的人名，并形成新的集合。
        List<String> nameList =
                StreamTest.personList().stream()
                        .filter(p -> "male".equals(p.getSex()))
                        .map(Person::getName)
                        .collect(Collectors.toList());
        nameList.forEach(System.out::println);
    }

    /**
     * 3.3 聚合（max/min/count)
     */
    @Test
    void maxMinCount() {
        // 1. 获取 String 集合中最长的元素 ["a", "b", "c", "dd", "ee"]
        Optional<String> max = Arrays.stream(new String[]{"a", "b", "c", "dd11", "ee"}).max(Comparator.comparing(String::length));
        max.ifPresent(s -> System.out.printf("最长的元素为：%s%n", s));
        // 2. 获取 Integer 集合中的最大值
        List<Integer> list = Arrays.asList(1, 5, 9, 8, 12, -13);
        // 自然排序
        Optional<Integer> max1 = list.stream().max(Integer::compareTo);
        max1.ifPresent(i -> System.out.printf("自然排序最大的数为：%d%n", i));
        // 绝对值最大的数
        Optional<Integer> max2 = list.stream().max(Comparator.comparingInt(Math::abs));
        max2.ifPresent(i -> System.out.printf("绝对值最大的数为：%d%n", i));
        // 3. 获取工资最高的人的住址
        Optional<String> max3 =
                StreamTest.personList().stream()
                        .max(Comparator.comparing(Person::getSalary))
                        .map(Person::getArea);
        max3.ifPresent(address -> System.out.printf("工资最高的人的住址为：%s%n", address));
        // 4. 获取工资高于 8000 的员工的个数
        long above8000Count = StreamTest.personList().stream().filter(p -> p.getSalary() > 8000).count();
        System.out.printf("工资高于8000的员工个数为：%d", above8000Count);
    }

    /**
     * 3.4 映射(map/flatMap)
     */
    @Test
    void mapFlatMap() {
        // 案例一：英文字符串数组的元素全部改为大写。
        String[] strArr = { "abcd", "bcdd", "defde", "fTr" };
        List<String> upperCaseList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(upperCaseList);
        // 案例二：整数数组每个元素+3。
        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> resultIntList = intList.stream().map(x -> x + 3).collect(Collectors.toList());
        System.out.println(resultIntList);
        // 将员工的薪资全部增加1000。
        System.out.printf("调薪前的薪资为：%s%n", StreamTest.personList());
        List<Person> upperSalaryList =
                StreamTest.personList().stream()
                        .peek(p -> p.setSalary(p.getSalary() + 1000))
                        .collect(Collectors.toList());
        System.out.printf("调薪前的薪资为：%s%n", StreamTest.personList());
        System.out.printf("调薪后的薪资为：%s%n", upperSalaryList);
        // 案例三：将两个字符数组合并成一个新的字符数组。
        // 处理前：["m,k,l,a", "1,3,5"]
        // 处理后：[m, k, l, a, 1, 3, 5, 7]
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> resultList =
                list.stream()
                        .flatMap(s -> Arrays.stream(s.split(",")))
                        .collect(Collectors.toList());
        System.out.println(resultList);
    }

    @Test
    void peek() {
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    /**
     * 3.5 归约(reduce)
     * 归约，也称缩减，顾名思义，是把一个流缩减成一个值，能实现对集合求和、求乘积和求最值操作。
     * TODO reduce 需要再理解？？？
     */
    @Test
    void reduce() {
        // 案例一：求Integer集合的元素之和、乘积和最大值。
        List<Integer> intList = Arrays.asList(1, 3, 2, 8, 11, 4);
        // 求和的方式一
        Optional<Integer> sum1 = intList.stream().reduce((x, y) -> x + y);
        sum1.ifPresent(sum -> System.out.printf("元素之和为：%d%n", sum));
        // 求和的方式二
        Optional<Integer> sum2 = intList.stream().reduce(Integer::sum);
        sum2.ifPresent(sum -> System.out.printf("元素之和为：%d%n", sum));
        // 求和的方式三
        Integer sum3 = intList.stream().reduce(0, Integer::sum);
        System.out.printf("元素之和为：%d%n", sum3);
        // 求乘积
        Optional<Integer> product = intList.stream().reduce((x, y) -> x * y);
        product.ifPresent(p -> System.out.printf("元素乘积为：%d%n", p));
        // 求最大值
        Integer max1 = intList.stream().reduce(Integer.MIN_VALUE, Integer::max);
        System.out.printf("元素最大值为：%d%n", max1);
        Optional<Integer> max2 = intList.stream().reduce((x, y) -> x > y ? x : y);
        max2.ifPresent(max -> System.out.printf("元素最大值为：%d%n", max));
        // 案例二：求所有员工的工资之和和最高工资。
        // 员工总工资之和
        Optional<Integer> sumSalary1 =
                StreamTest.personList().stream()
                        .map(Person::getSalary)
                        .reduce(Integer::sum);
        sumSalary1.ifPresent(s -> System.out.printf("员工总工资为：%d%n", s));
        // 员工总工资之和二
        Integer sumSalary2 =
                StreamTest.personList().stream()
                        .reduce(0, (s, p) -> s += p.getSalary(), (s1, s2) -> s1 + s2);
        System.out.printf("员工总工资为：%d%n", sumSalary2);
        // 最高工资
        Integer maxSalary =
                StreamTest.personList().stream()
                        .map(Person::getSalary)
                        .reduce(Integer.MIN_VALUE, Integer::max);
        System.out.printf("最高工资为：%d%n", maxSalary);
        Integer maxSalary2 =
                StreamTest.personList().stream()
                        .reduce(0, (m, p) -> m > p.getSalary() ? m : p.getSalary(), Integer::max);
        System.out.printf("最高工资为：%d%n", maxSalary2);
    }

    /**
     * 3.6 收集(collect)
     * collect，收集，可以说是内容最繁多、功能最丰富的部分了。
     * 从字面上去理解，就是把一个流收集起来，最终可以是收集成一个值也可以收集成一个新的集合。
     *
     * <p>collect主要依赖java.util.stream.Collectors类内置的静态方法。
     */
    @Nested
    @DisplayName("collect相关示例")
    class Collect {

        /**
         * 3.6.1 归集(toList/toSet/toMap)
         *
         * 因为流不存储数据，那么在流中的数据完成处理后，需要将流中的数据重新归集到新的集合里。
         * toList、toSet和toMap比较常用，另外还有toCollection、toConcurrentMap等复杂一些的用法。
         */
        @Test
        void toCollection() {
            // 选出集合（1, 6, 3, 4, 6, 7, 9, 6, 20）中的偶数并组成新的集合
            List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
            List<Integer> evenList = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
            System.out.println(evenList);
            Set<Integer> evenSet = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());
            System.out.println(evenSet);
            // 选出员工集合中所有工资大于8000的员工，并以员工姓名为key，员工为value，构建一个map
            Map<String, Person> resultMap = StreamTest.personList().stream()
                    .filter(p -> p.getSalary() > 8000)
                    .collect(Collectors.toMap(Person::getName, p -> p));
            System.out.println(resultMap);
        }

        /**
         *
         * <ul>Collectors提供了一系列用于数据统计的静态方法：</ul>
         * <li>计数：count
         * <li>平均值：averagingInt、averagingLong、averagingDouble
         * <li>最值：maxBy、minBy
         * <li>求和：summingInt、summingLong、summingDouble
         * <li>统计以上所有：summarizingInt、summarizingLong、summarizingDouble
         */
        @Test
        void countAveraging() {
            // 求和
            Long count = StreamTest.personList().stream().filter(p -> p.getSalary() > 8000).collect(Collectors.counting());
            System.out.printf("总和为：%d%n", count);
            // 求平均工资
            Double average = StreamTest.personList().stream().collect(Collectors.averagingDouble(Person::getSalary));
            System.out.println("平均工资为：" + average);
            // 求最高工资
            Optional<Integer> maxSalary = StreamTest.personList().stream().map(Person::getSalary).max(Integer::compare);
            maxSalary.ifPresent(s -> System.out.printf("最高工资为：%d%n", s));
            // 求工资之和
            Integer sum = StreamTest.personList().stream().collect(Collectors.summingInt(Person::getSalary));
            System.out.printf("工资之和为：%d%n", sum);
            // 案例：统计员工人数、平均工资、工资总额、最高工资。
            DoubleSummaryStatistics collect = StreamTest.personList().stream().collect(Collectors.summarizingDouble(Person::getSalary));
            System.out.println("员工所有统计信息为：" + collect);
        }

        /**
         * <ul>3.6.3 分组(partitioningBy/groupingBy)
         * <li>分区：将stream按条件分为两个Map，比如员工按薪资是否高于8000分为两部分。
         * <li>分组：将集合分为多个Map，比如员工按性别分组。有单级分组和多级分组。
         */
        @Test
        void partitioningByGroupingBy() {
            // 案例：将员工按薪资是否高于8000分为两部分；
            Map<Boolean, List<Person>> isAbove8000 =
                    StreamTest.personList().stream()
                            .collect(Collectors.partitioningBy(p -> p.getSalary() > 8000));
            System.out.println("大于8000的员工集合为：" + isAbove8000.get(Boolean.TRUE));
            System.out.println("小于等于8000的员工集合为：" + isAbove8000.get(Boolean.FALSE));
            // 将员工按性别分组
            Map<String, List<Person>> sexMap = StreamTest.personList().stream().collect(Collectors.groupingBy(Person::getSex));
            System.out.println("按性别的分组结果为：" + sexMap);
            // 将员工先按性别分组，再按地区分组
            Map<String, Map<String, List<Person>>> group = StreamTest.personList().stream()
                    .collect(Collectors.groupingBy(Person::getSex,
                            Collectors.groupingBy(Person::getArea)));
            System.out.println("分组后的结果为：" + group);
        }

        /**
         * 3.6.4 接合(joining)
         * joining可以将stream中的元素用特定的连接符（没有的话，则直接连接）连接成一个字符串。
         */
        @Test
        void joining() {
            // 将所有员工的名字合并，中间使用 “,” 号分隔开
            String nameJoined = StreamTest.personList().stream()
                    .map(Person::getName)
                    .collect(Collectors.joining(","));
            System.out.println("员工名字合并结果为：" + nameJoined);
        }

        /**
         * 3.6.5 归约(reducing)
         * Collectors类提供的reducing方法，相比于stream本身的reduce方法，增加了对自定义归约的支持。
         *
         * TODO 更好的例子
         */
        void reducing() {
            // 每个员工减去起征点后的薪资之和
            StreamTest.personList().stream().collect(Collectors.reducing(0, p -> p.getSalary() - 1500, (i, j) -> i + j - 5000));
            // stream的reduce
        }

        /**
         * 3.7 排序(sorted)
         * sorted，中间操作。有两种排序：
         * <li>sorted()：自然排序，流中元素需实现Comparable接口
         * <li>sorted(Comparator com)：Comparator排序器自定义排序
         *
         */
        @Test
        void sorted() {
            // 案例：将员工按工资由高到低（工资一样则按年龄由大到小）排序
            // 按工资升序排序（自然排序）
            List<Person> sortedPersonList =
                    StreamTest.personList().stream()
                            .sorted(Comparator.comparing(Person::getSalary))
                            .collect(Collectors.toList());
            System.out.println("按工资升序排序（自然排序）：" + sortedPersonList);
            // 按工资倒序排序
            List<Person> sortedDescPersonList =
                    StreamTest.personList().stream()
                            .sorted(Comparator.comparing(Person::getSalary).reversed())
                            .collect(Collectors.toList());
            System.out.println("按工资倒序排序：" + sortedDescPersonList);
            // 先按工资再按年龄升序排序
            List<Person> sortedBySalaryAndAgeList = StreamTest.personList().stream()
                    .sorted(Comparator.comparing(Person::getSalary)
                            .thenComparing(Person::getAge))
                    .collect(Collectors.toList());
            System.out.println("先按工资再按年龄升序排序：" + sortedBySalaryAndAgeList);
            // 先按工资再按年龄自定义排序（降序）
            List<Person> customSortedList = StreamTest.personList().stream()
                    .sorted((p1, p2) -> {
                        if (p1.getSalary() == p2.getSalary()) {
                            return p1.getAge() - p2.getAge();
                        } else {
                            return p2.getSalary() - p1.getSalary();
                        }
                    }).collect(Collectors.toList());
            System.out.println("自定义排序：" + customSortedList);
        }

        /**
         * 3.8 提取/组合
         */
        @Test
        void distinctLimitSkip() {
            Stream<String> stream1 = Stream.of("a", "b", "c", "d");
            Stream<String> stream2 = Stream.of("d", "e", "f", "g");
            // concat:合并两个流 distinct：去重
            List<String> list1 = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
            System.out.println(list1);
            // limit：限制从流中获得前n个数据
            List<Integer> list2 = Stream.iterate(1, x -> x + 2).limit(3).collect(Collectors.toList());
            System.out.println(list2);
            // skip：跳过前n个数据
            List<Integer> list3 = Stream.iterate(1, x -> x + 2).skip(2).limit(5).collect(Collectors.toList());
            System.out.println(list3);
        }
    }


}
