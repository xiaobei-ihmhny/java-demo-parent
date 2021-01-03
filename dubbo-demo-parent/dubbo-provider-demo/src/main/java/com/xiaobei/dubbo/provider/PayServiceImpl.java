package com.xiaobei.dubbo.provider;

import com.xiaobei.dubbo.api.IPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/02 20:29
 */
public class PayServiceImpl implements IPayService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PayServiceImpl.class);

    @Override
    public String pay(String info) {
        LOGGER.info("收到客户端请求，请求内容为：{}", info);
        return info;
    }
}
