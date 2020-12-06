package com.xiaobei.spring.web.demo.testcontext;

import com.xiaobei.spring.web.demo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 使用注解 {@link org.springframework.test.context.transaction.BeforeTransaction}
 * 和注解 {@link org.springframework.test.context.transaction.AfterTransaction}
 * 指定某些操作在事务开始前或结束后执行
 *
 * <p>
 *     若应用上下文中出现了多个 {@link org.springframework.transaction.PlatformTransactionManager}，
 *     则可通过 {@link Transactional#transactionManager()}
 *     或者 {@link Transactional#value()} 指明要使用的事务管理器
 * 参见：https://docs.spring.io/spring-framework/docs/5.3.1/reference/html/testing.html#testcontext-tx-before-and-after-tx
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/06 22:36
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TransactionManagementTest.AppConfig.class)
@Transactional
@Commit
public class RunningCodeOutSideOfATransactionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 事务开始前执行
     */
    @BeforeTransaction
    public void beforeTransaction() {
        Map<String, Object> user = jdbcTemplate.queryForMap("SELECT `username`, `age` FROM `user` WHERE id = 3");
        System.out.println("事务开始前：" + user);
    }

    /**
     * 事务结束后执行
     */
    @AfterTransaction
    public void afterTransaction() {
        Map<String, Object> user = jdbcTemplate.queryForMap("SELECT `username`, `age` FROM `user` WHERE id = 3");
        System.out.println("事务结束后：" + user);
    }

    @Test
    public void testCodeOutsideOfTx() {
        int update = jdbcTemplate.update("UPDATE user SET `user`.age = ? WHERE `user`.id = ?", 4, 3);
        System.out.println(update);
        Map<String, Object> user = jdbcTemplate.queryForMap("SELECT `username`, `age` FROM `user` WHERE id = 3");
        System.out.println("事务进行中：" + user);
    }
}
