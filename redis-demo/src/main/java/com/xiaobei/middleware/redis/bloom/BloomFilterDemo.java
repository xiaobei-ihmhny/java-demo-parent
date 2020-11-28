package com.xiaobei.middleware.redis.bloom;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/11/27 21:58
 */
@SuppressWarnings("UnstableApiUsage")
public class BloomFilterDemo {

    /**
     * 数据量
     */
    private static final int insertCounts = 1000000;
//    private static final long insertCounts = 10000000000L;

    public static void main(String[] args) {
        // 初始化一个
        BloomFilter<String> bloomFilter = BloomFilter
                .create(Funnels.stringFunnel(Charsets.UTF_8), insertCounts);
        // 存放所有实际存在的key，用过判断 key 是否存在
        List<String> allKeys = new ArrayList<>(insertCounts);
        // 向三个容器中初始化100w个随机字符串
        for (int i = 0; i < insertCounts; i++) {
            String uuid = UUID.randomUUID().toString();
            // 放入布隆过滤器中
            bloomFilter.put(uuid);
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
            if(bloomFilter.mightContain(str)) {
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
