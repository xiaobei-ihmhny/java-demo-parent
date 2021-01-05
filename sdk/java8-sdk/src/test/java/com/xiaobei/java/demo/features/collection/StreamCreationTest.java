package com.xiaobei.java.demo.features.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream 创建示例
 *
 *
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-05 10:35:35
 */
@DisplayName("Stream 创建示例")
public class StreamCreationTest {

    /**
     * 方法一：通过 {@link Collection#stream()} 方法用集合创建流
     *
     * <p>stream 和 parallelStream 的区别</p>
     * <p>
     *     stream是顺序流，由主线程按顺序对流执行操作，
     *     而parallelStream是并行流，内部以多线程并行执行的方式对流进行操作，
     *     但前提是流中的数据处理没有顺序要求。例如筛选集合中的奇数，两者的处理不同之处：
     *     <a href="https://www.processon.com/diagraming/5ff40a32f346fb340de4bd2e">stream vs parallelStream</a>
     *     如果流中的数据量足够大，并行流可以加快处速度。
     *     除了直接创建并行流，还可以通过parallel()把顺序流转换成并行流：
     *     {@code Optional<Integer> findFirst = list.stream().parallel().filter(x->x>6).findFirst();}
     * </p>
     */
    @Test
    void byCollectionStream() {
        List<String> list = Arrays.asList("a", "b", "c");
        // 创建一个顺序流
        Stream<String> stream = list.stream();
        // 创建一个并行流
        Stream<String> parallelStream = list.parallelStream();
        stream.forEach(System.out::println);
        parallelStream.forEach(System.out::println);
    }

    /**
     * 方法二：通过 {@link Arrays#stream(Object[])} 方法用数组创建流
     */
    @Test
    void byArraysStream() {
        int[] array = {1, 3, 5, 7};
        IntStream intStream = Arrays.stream(array);
        intStream.forEach(System.out::println);
    }

    /**
     * 方法三：通过 {@code Stream} 类的静态方法
     * 1. {@link Stream#of(Object[]) Stream.of()}
     * 2. {@link Stream#iterate(Object, UnaryOperator) Stream.iterate()}
     * 3. {@link Stream#generate(Supplier) Stream.generate()}
     */
    @Test
    void byStreamStaticMethod() {
        // 1. 运行结果：1 2 3 4 5 6
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5, 6);
        stream1.map(x -> x + " ").forEach(System.out::print);
        System.out.println();
        // 2. 运行结果：0 3 6 9
        Stream<Integer> stream2 = Stream.iterate(0, x -> x + 3).limit(4);
        stream2.map(x -> x + " ").forEach(System.out::print);
        System.out.println();
        //3. 运行结果：0.6567938961266488 0.32258874757652367 0.9213784620496248
        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
        stream3.map(x -> x + " ").forEach(System.out::print);
    }

    /**
     * 从文件中获取 {@code stream}
     */
    @Test
    void byFile() throws URISyntaxException, IOException {
        Path path = Paths.get(this.getClass().getResource("/stream.txt").toURI());
        Stream<String> stream = Files.lines(path);
        stream.forEach(System.out::println);
    }

    // TODO 网络文件如何读取？？？？2021年1月5日14:20:17
    @Test
    void byNetWorkFile() throws URISyntaxException, IOException {
        URL url = new URL("https://oss-dev1.tlwok.com/stream.txt");
    }
}
