package com.xiaobei.spring.web.demo.testcontext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 编程方式的事务管理
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 21:48
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TransactionManagementTest.AppConfig.class)
public class ProgrammaticTransactionManagementTest extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * TestTransaction.flagForRollback();
     * 标记当前测试方法的事务为回滚，表明方法调用结束之后事务需要回滚
     * TestTransaction.flagForCommit();
     * 标记当前测试方法的事务为提交，表明方法调用结束之后事务需要提交
     * TestTransaction.start();
     * 开启一个新的事务
     * TestTransaction.end();
     * 强制提交或回滚当前事务
     * TestTransaction.isActive();
     * 判断当前事务是否已激活
     * TestTransaction.isFlaggedForRollback();
     * 查看当前的回滚标记
     */
    @Test
    public void testTransaction() {
        int update = jdbcTemplate.update("UPDATE user SET `user`.age = ? WHERE `user`.id = ?", 1, 3);
        System.out.println(update);
    }

}
