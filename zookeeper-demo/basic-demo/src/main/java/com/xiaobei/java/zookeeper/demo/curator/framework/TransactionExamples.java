package com.xiaobei.java.zookeeper.demo.curator.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/26 20:16
 */
public class TransactionExamples {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionExamples.class);
    /**
     * 该示例展示了如何使用 ZooKeeper 的事务
     * TODO 是否是说这些消息要么全部成功，要么全总失败？？？
     */
    @Test
    public void transaction() throws Exception {
        CuratorFramework client = CreateClientExamples.createSimple(CreateClientExamples.CONNECTION_STRING);
        client.start();
        CuratorOp createOp = client.transactionOp().create().forPath("/data/xiaobei", "18".getBytes());
        CuratorOp setOp = client.transactionOp().setData().forPath("/date/xiaobei", "16".getBytes());
        CuratorOp deleteOp = client.transactionOp().delete().forPath("/other");

        List<CuratorTransactionResult> results = client.transaction().forOperations(createOp, setOp, deleteOp);

        for (CuratorTransactionResult result : results) {
            System.out.println(result.getForPath() + " - " + result.getType());
        }

        System.out.println(results);
    }
}
