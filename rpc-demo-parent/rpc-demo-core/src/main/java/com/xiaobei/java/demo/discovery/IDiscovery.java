package com.xiaobei.java.demo.discovery;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-11 13:16:16
 */
public interface IDiscovery {

    /**
     * 根据服务名称找到一个服务地址
     * @param serviceName
     * @return
     */
    String discovery(String serviceName);
}
