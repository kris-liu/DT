package cn.blogxin.dt.client.tm;

import cn.blogxin.dt.client.exception.DTException;
import cn.blogxin.dt.client.id.IdGenerator;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * 事务管理器。控制分布式事务的边界，负责开启一个分布式事务，并最终发起分布式事务提交或回滚的决议。
 * @author kris
 */
public class TransactionManager {

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private LocalTransactionSynchronization localTransactionSynchronization;

    public void start(String suffix) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new DTException("分布式事务需要在一个本地事务环境中开启");

        }

        // 生成事务ID
        String xid = idGenerator.getId(suffix);

        // 插入主事务记录



        // 更新主事务记录



        // 添加事务同步器
        TransactionSynchronizationManager.registerSynchronization(localTransactionSynchronization);

    }



}
