package com.xiaobei.dubbo.spi;

import org.apache.dubbo.common.compiler.Compiler;
import org.apache.dubbo.common.extension.ExtensionFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * dubbo spi 示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-05 23:07:07
 */
@DisplayName("Dubbo SPI 相关机制")
public class SPIDemo {

    /**
     * dubbo 静态扩展点
     */
    @Test
    void extension() {
        // 静态扩展点
//        Protocol myProtocol = ExtensionLoader.getExtensionLoader(Protocol.class)
//                .getExtension("myProtocol");
        // 自适应扩展点
        ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension();
        Compiler compiler = ExtensionLoader.getExtensionLoader(Compiler.class).getAdaptiveExtension();
        System.out.println(compiler);
    }
}
