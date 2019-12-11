package com.xiaobei.java.demo.discovery;

import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-10 20:33:33
 */
public interface LoadBalance {

    /**
     * 从服务列表中获取一个服务
     * @param serviceList
     * @return
     */
    String select(List<String> serviceList);
}