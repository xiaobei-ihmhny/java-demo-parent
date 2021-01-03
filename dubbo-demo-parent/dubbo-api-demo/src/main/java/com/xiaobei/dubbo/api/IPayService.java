package com.xiaobei.dubbo.api;

/**
 * 支付服务 api
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/02 20:28
 */
public interface IPayService {

    /**
     * 暴露的服务
     * 完成支付功能
     * @param info
     * @return
     */
    String pay(String info);
}
