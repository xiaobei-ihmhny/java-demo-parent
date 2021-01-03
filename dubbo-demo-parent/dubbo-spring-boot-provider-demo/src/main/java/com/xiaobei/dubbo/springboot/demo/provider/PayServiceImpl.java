package com.xiaobei.dubbo.springboot.demo.provider;

import com.xiaobei.dubbo.api.IPayService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/03 16:14
 */
@Service
public class PayServiceImpl implements IPayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayServiceImpl.class);

    @Override
    public String pay(String info) {
        LOGGER.info("请求来了了了了了了...，参数为 {}", info);
        return info;
    }
}
