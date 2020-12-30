package com.xiaobei.java.demo.features.collection;

import com.xiaobei.java.demo.features.domain.Apple;
import com.xiaobei.java.demo.features.domain.Dish;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import sun.reflect.generics.tree.Tree;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 示例 java 中的 Stream 操作
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-30 12:43:43
 */
@DisplayName("Stream API使用示例")
public class CollectionsTest {

    private static List<Apple> list;

    protected static List<Dish> dishList;

    @BeforeAll
    public static void init() {
        // 初始化 list
        list = Arrays.asList(
                new Apple("green", 100),
                new Apple("red", 200),
                new Apple("green", 100),
                new Apple("red", 200),
                new Apple("yellow", 200),
                new Apple("green", 250));
        // 初始化 dishList
        dishList = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 400, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 400, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
    }

    /**
     * 从指定的集合 {@code list} 中选中指定颜色的 {@link Apple}
     * <p>
     * java 8 之前版本中对集合过滤并打印
     *
     * @param needColor
     */
    @ParameterizedTest
    @ValueSource(strings = {"green"})
    void listByColorFilterThenPrintBeforeJava8(String needColor) {
        List<Apple> filteredList = new ArrayList<>();
        for (Apple apple : list) {
            String currentColor = apple.getColor();
            if (needColor.equals(currentColor)) {
                filteredList.add(apple);
            }
        }
        System.out.println(filteredList);
    }

    /**
     * 从指定的集合 {@code list} 中选中指定颜色的 {@link Apple}
     * <p>
     * java 8 之后版本中过滤指定集合并打印
     *
     * @param needColor
     */
    @ParameterizedTest
    @ValueSource(strings = {"green"})
    void listByColorFilterThenPrint(String needColor) {
        List<Apple> filteredList = list.stream()
                .filter(apple -> needColor.equals(apple.getColor()))
                .collect(Collectors.toList());
        Optional.of(filteredList).ifPresent(System.out::println);
    }

    /**
     * 按照颜色对给定集合进行分组并打印
     */
    @Test
    void groupByFunctionBeforeJava8() {
        Map<String, List<Apple>> resultMap = new HashMap<>(8);
        for (Apple apple : list) {
            String color = apple.getColor();
            List<Apple> currentColorList;
            if ((currentColorList = resultMap.get(color)) == null) {
                currentColorList = new ArrayList<>();
                resultMap.put(color, currentColorList);
            }
            currentColorList.add(apple);
        }
        System.out.println(resultMap);
    }

    /**
     * 按照颜色对给定集合进行分组并打印
     */
    @Test
    void groupByFunction() {
        Map<String, List<Apple>> resultMap = list.stream()
                .collect(Collectors.groupingBy(Apple::getColor));
        Optional.ofNullable(resultMap).ifPresent(System.out::println);
    }

    @Nested
    @DisplayName("高级应用示例1")
    class AdvanceDemo1 {

        /**
         * {@link Collectors#averagingDouble(ToDoubleFunction)} 计算平均数
         */
        @Test
        void averaging() {
            Double averageCalories = dishList.stream().collect(Collectors.averagingDouble(Dish::getCalories));
            System.out.println(averageCalories);
            Double averageLong = dishList.stream().collect(Collectors.averagingLong(Dish::getCalories));
            System.out.println(averageLong);
            Double averageInt = dishList.stream().collect(Collectors.averagingInt(Dish::getCalories));
            System.out.println(averageInt);
        }

        /**
         * 获取集合 {@code dishList} 中所有元素的平均卡路里
         * This average is 466.6666666666667
         */
        @Test
        @DisplayName("collectingAndThen")
        void collectingAndThen() {
            String result = dishList.stream().collect(
                    Collectors.collectingAndThen(Collectors.averagingInt(Dish::getCalories),
                            calories -> "This average is " + calories));
            System.out.println(result);
        }

        /**
         * 获取集合 {@code dishList} 中所有元素中类型相同的元素的卡路里总和
         * 结果形如：{@code Map<Dish.Type, Double> }
         */
        @Test
        void collectingAndThen2() {
            Map<Dish.Type, Integer> resultMap =
                    dishList.stream()
                            .collect(Collectors.groupingBy(Dish::getType,
                                    Collectors.summingInt(Dish::getCalories)));
            System.out.println(resultMap);
        }

        /**
         * 统计卡路里相同的食物的卡路里总和
         * <p>
         * {@link Collectors#groupingBy(Function, Collector)}
         * {@link Collectors#groupingBy(Function, Supplier, Collector)}
         */
        @Test
        void groupingByFunctionAndSupplierAndCollector() {
            // 使用默认的类型
            Map<Integer, Integer> sameCaloriesToSumMap =
                    dishList.stream()
                            .collect(Collectors.groupingBy(Dish::getCalories,
                                    Collectors.summingInt(Dish::getCalories)));
            System.out.println(sameCaloriesToSumMap);
            System.out.println(sameCaloriesToSumMap.getClass());
            // 指定 map 的类型
            TreeMap<Integer, Integer> sameCaloriesToSumCustomMap =
                    dishList.stream()
                            .collect(Collectors.groupingBy(Dish::getCalories,
                                    TreeMap::new,
                                    Collectors.summingInt(Dish::getCalories)));
            System.out.println(sameCaloriesToSumCustomMap);
            System.out.println(sameCaloriesToSumCustomMap.getClass());
        }

    }

    @Nested
    @DisplayName("高级示例2")
    class AdvanceDemo2 {

        /**
         * 若
         */
        @Test
        void collectionToMap() {

        }
    }


}