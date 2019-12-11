package com.xiaobei.java.demo.discovery;

import java.util.List;
import java.util.Random;

/**
 * 随机负载实现
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-10 20:35:35
 */
public class RandomLoadBanlance extends AbstractLoadBanlance {

    /**
     *
     * @param serviceList
     * @return
     */
    @Override
    protected String selectOne(List<String> serviceList) {
        Random random = new Random();
        int length = serviceList.size();
        return serviceList.get(random.nextInt(length));
    }
}