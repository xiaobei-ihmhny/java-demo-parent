package com.xiaobei.middleware.redis.bloom;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 用 guava 和 redisson 分别测试一百万条数据
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/27 21:58
 */
@SuppressWarnings("UnstableApiUsage")
public class BloomFilterDemo {

    /**
     * 数据量
     */
    private static final int guavaInsertCounts = 1000000;
    private static final int redissonInsertCounts = 10000;

    /**
     * guava 中的 bloomFilter的添加效率要远高于 redisson
     * guava 中提供的 bloomFilter 实现 {@link BloomFilter}
     */
    @Test
    public void guavaBloomFilter() {
        // 初始化一个
        BloomFilter<String> bloomFilter = BloomFilter
                .create(Funnels.stringFunnel(Charsets.UTF_8), guavaInsertCounts);
        bloomFilterTest(bloomFilter::put, bloomFilter::mightContain, guavaInsertCounts);
    }

    /**
     * redisson 提供的 bloomFilter 的实现：{@link RBloomFilter}
     */
    @Test
    public void redissonBloomFilter() {
        Config config = new Config();
        config.useSingleServer()
                .setPassword("centos01")
                .setAddress("redis://192.168.163.101:6379");
        RedissonClient redisson = Redisson.create(config);
        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("bf");
        bloomFilter.tryInit(redissonInsertCounts, 0.03);
        bloomFilterTest(bloomFilter::add, bloomFilter::contains, redissonInsertCounts);
    }

    /**
     * 测试 bloomFilter 的性能、准确率及误判率
     * @param toBloomFilter bloomFilter 提供的添加元素方法
     * @param mightContainsFunc bloomFilter 提供的验证元素是否存在的方法
     * @param insertCounts bloomFilter 初始化容量大小
     */
    public void bloomFilterTest(Consumer<String> toBloomFilter,
                                Function<String, Boolean> mightContainsFunc,
                                Integer insertCounts) {
        // 存放所有实际存在的key，用过判断 key 是否存在
        List<String> allKeys = new ArrayList<>(insertCounts);
        // 向三个容器中初始化100w个随机字符串
        for (int i = 0; i < insertCounts; i++) {
            String uuid = UUID.randomUUID().toString();
            // 放入布隆过滤器中
            toBloomFilter.accept(uuid);
            System.out.println("================"+i);
            // 放入数组中
            allKeys.add(uuid);
        }
        // 正确判断的次数
        int rightCount = 0;
        // 错误判断的次数
        int wrongCount = 0;
        int sample = 10000;
        for (int i = 0; i < sample; i++) {
            // 当前下标可以被100整除时取一个值，否则随机生成一个
            // 0 ~ 10000 之间可以被100整除的数有 100 个
            String str = i % 100 == 0 ? allKeys.get(i) : UUID.randomUUID().toString();
            // 判断是否存在
            if(mightContainsFunc.apply(str)) {
                // 说明 BloomFilter 认为存在
                if(allKeys.contains(str)) {
                    // 说明 确实实际存在
                    rightCount++;
                    continue;
                }
                // 说明实际不存在，但 BloomFilter 认为存在，即出现的误判
                wrongCount++;
            }
        }

        // 对判断结果进行统计
        int nonExistsEleCount = sample - rightCount;
        float errorRate = (float) wrongCount / nonExistsEleCount;
        float correctRate = (float) (nonExistsEleCount - wrongCount) / nonExistsEleCount;
        System.out.printf("在 %d 个元素中，判断 100 个实际存在的元素，" +
                "布隆过滤器认为存在的个数为：%d\n", insertCounts, rightCount);
        System.out.printf("在 %d 个元素中，判断 %d 个实际不存在的元素，" +
                        "误认为存在的个数：%d, 命中率为：%4.3f, 误判率为：%4.3f\n",
                insertCounts, nonExistsEleCount, wrongCount, correctRate, errorRate);
    }
}
