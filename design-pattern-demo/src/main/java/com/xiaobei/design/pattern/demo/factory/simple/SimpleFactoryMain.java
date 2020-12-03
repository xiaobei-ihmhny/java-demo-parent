package com.xiaobei.design.pattern.demo.factory.simple;

/**
 * <p>以下为 简单工厂 设计模式的说明</p>
 *
 * <p>
 *     总结起来就是一个工厂类，一个产品接口（其实也可以是一个抽象类，甚至一个普通的父类，
 *     但通常我们觉得接口是最稳定的，所以基本上不需要考虑普通父类的情况），
 *     和一群实现了产品接口的具体产品，而这个工厂类，根据传入的参数去创造一个具体的实现类，
 *     并向上转型为接口作为结果返回。
 * </p>
 *
 * 优点：
 *
 * 缺点：
 *   1. 工厂类的职责相对过重，不易于扩展过于复杂的产品结构。
 *   2. 不符合“开闭原则”
 *
 *
 * TODO 补充简单工厂在常见jar中的运用！！
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 16:35:35
 */
public class SimpleFactoryMain {
}