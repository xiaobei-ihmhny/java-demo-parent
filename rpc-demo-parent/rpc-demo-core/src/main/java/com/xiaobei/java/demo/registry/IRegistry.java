package com.xiaobei.java.demo.registry;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-10 17:23:23
 */
public interface IRegistry {

    /**
     * 服务注册
     * @param serviceName
     * @param serviceAddress
     */
    void registry(String serviceName, String serviceAddress);
}
