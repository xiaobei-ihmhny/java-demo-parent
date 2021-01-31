CREATE DATABASE IF NOT EXISTS mybatis;
USE mybatis;
# 建表语句
DROP TABLE IF EXISTS USER;
CREATE TABLE `user` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `username` VARCHAR(128) NOT NULL COMMENT '用户名',
    `age` INT UNSIGNED NOT NULL COMMENT '年龄',
    `birth_place` VARCHAR(64) DEFAULT NULL COMMENT '出生地',
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
# 测试数据
INSERT INTO `user` (`id`, `username`, `age`, `birth_place`)
VALUES (NULL, 'huihui', 18, '广东'),
(NULL, 'beibei', 20, '洛阳'),
(NULL, 'tietie', 1, '北京');