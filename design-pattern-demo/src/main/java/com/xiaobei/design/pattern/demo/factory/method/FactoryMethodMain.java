package com.xiaobei.design.pattern.demo.factory.method;

/**
 *
 * TODO 完善具体的框架中对工厂方法模式的应用
 * <p> 1. java jdbc中的 {@link java.sql.Driver Driver} 和 {@link java.sql.Connection Connection}，
 * {@code Driver} 相当于是数据库连接工厂，{@code Connection} 相当于是抽象产品，具体的数据库实现厂商
 * 需要提供一个具体的工厂类（比如 mysql 驱动 {@link com.mysql.cj.jdbc.Driver}）
 * 和一个具体的产品（比如 mysql 数据库连接 {@link com.mysql.cj.jdbc.ConnectionImpl}）
 * <p> 2.
 *
 * <p> <a href="https://www.processon.com/diagraming/5fc8b0205653bb7d2b28265c">UML类图地址</a>
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-03 17:08:08
 */
public class FactoryMethodMain {
}