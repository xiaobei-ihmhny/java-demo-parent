package com.xiaobei.spi.demo.dubbo;

/**
 * <ul>Dubbo SPI 相关机制</ul>
 * <li>1. 在 classpath 下创建目录，其中目录可以有几种选择：
 * <tt>META-INF/services/</tt>、<tt>META-INF/dubbo/</tt>、<tt>META-INF/dubbo/internal/</tt>，
 * ，并基于 SPI 接口创建文件</li>
 * <li>2. 文件名称为要实现的接口名称，其内容为 <tt>key/value</tt> 的形式</li>
 *
 * <p>Dubbo的扩展点非常之多，可以针对协议、拦截、集群、路由、负载均衡、序列化、容器… 几乎里面用到的所有功能，
 * 都可以实现自己的扩展。</p>
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-07 10:57:57
 */
public class DubboSpiMain {
}
