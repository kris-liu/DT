package cn.blogxin.dt.client.tm;

import cn.blogxin.dt.client.context.DTContext;
import cn.blogxin.dt.client.context.DTContextEnum;
import cn.blogxin.dt.client.exception.DTException;
import cn.blogxin.dt.client.id.IdGenerator;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * 事务管理器。控制分布式事务的边界，负责开启一个分布式事务，并最终发起分布式事务提交或回滚的决议。
 * @author kris
 */
public class TransactionManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private LocalTransactionSynchronization localTransactionSynchronization;

    public void start(String suffix) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new DTException("分布式事务需要在一个本地事务环境中开启");

        }

        String xid = idGenerator.getId(suffix);
        DTContext.set(DTContextEnum.XID, xid);


        // 插入主事务记录



        // 更新主事务记录



        // 添加事务同步器
        TransactionSynchronizationManager.registerSynchronization(localTransactionSynchronization);

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
