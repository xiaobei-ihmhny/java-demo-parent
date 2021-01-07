package com.xiaobei.spi.demo.java;

/**
 * <ul>java spi相关说明</ul>
 * <li>1. 在 classpath 目录下需要创建一个目录，其结构为：<tt>META-INF/services/<tt> </li>
 * <li>2. 在该目录下创建一个文件，其中文件名为对应服务接口的全路径名</li>
 * <li>3. 文件内容为该服务接口对应的所有实现类的全路径名，每个实现一行</li>
 * <li>4. 文件编码必须为 <tt>UTF-8</tt> </li>
 * <li>5. 最终通过 {@link java.util.ServiceLoader ServiceLoader} 的加载机制来加载对应实现</li>
 *
 * <p>SPI的缺点</p>
 * <li>1. JDK标准的 SPI 会一次加载实例化扩展点的所有实现，
 * 当存在多个实现类，而其中一些实现类又是不需要的时候会造成资源浪费。</li>
 * <li>2. 如果扩展点加载失败，会导致调用方报错，而这个错误很难定位。</li>
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-07 10:39:39
 */
public class JavaSpiMain {
}
