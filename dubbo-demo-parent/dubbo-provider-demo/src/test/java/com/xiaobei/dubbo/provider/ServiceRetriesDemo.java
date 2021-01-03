package com.xiaobei.dubbo.provider;

import com.xiaobei.dubbo.api.IPayService;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * 重试策略配置
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021/01/04 07:18
 */
@Service(retries = 2, methods = {// 3. 服务提供者层面
        @Method(name = "pay", retries = 2)// 4. 服务提供者方法层面
})
public class ServiceRetriesDemo {

    @Reference(retries = 2, methods = {// 5. 服务调用者层面
            @Method(name = "pay", retries = 2)// 6. 服务调用者方法层面
    })
    private IPayService payService;

    void pay() {
        //....
    }
}
