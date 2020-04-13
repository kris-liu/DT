package cn.blogxin.dt.client.tm;

import cn.blogxin.dt.client.context.ActionContext;
import cn.blogxin.dt.client.context.DTContext;
import cn.blogxin.dt.client.context.DTContextEnum;
import cn.blogxin.dt.client.exception.DTException;
import cn.blogxin.dt.client.id.IdGenerator;
import cn.blogxin.dt.client.log.entity.Activity;
import cn.blogxin.dt.client.log.enums.ActivityStatus;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import cn.blogxin.dt.client.rm.ResourceManager;
import cn.blogxin.dt.client.spring.DistributedTransactionProperties;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 事务管理器。控制分布式事务的边界，负责开启一个分布式事务，并最终发起分布式事务提交或回滚的决议。
 *
 * @author kris
 */
public class TransactionManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private DistributedTransactionProperties distributedTransactionProperties;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private ResourceManager dtResourceManager;

    @Resource
    private TwoPhaseTransactionSynchronization twoPhaseTransactionSynchronization;

    public void start() {
        start(null);
    }

    public void start(String suffix) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new DTException("分布式事务需要在一个本地事务环境中开启");
        }
        try {
            String xid = idGenerator.getId(suffix);
            DTContext.set(DTContextEnum.XID, xid);
            Activity activity = initActivity();
            DTContext.set(DTContextEnum.START_TIME, activity.getStartTime());
            DTContext.set(DTContextEnum.TIMEOUT_TIME, activity.getTimeoutTime());
            DTContext.set(DTContextEnum.ACTIVITY, activity);
            DTContext.set(DTContextEnum.ACTION_CONTEXT, new ActionContext());
            activityRepository.insert(activity);
            activityRepository.updateStatus(activity.getXid(), activity.getStatus(), ActivityStatus.COMMIT.getStatus());
        } finally {
            TransactionSynchronizationManager.registerSynchronization(twoPhaseTransactionSynchronization);
        }
    }

    private Activity initActivity() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeoutTime = now.plusSeconds(distributedTransactionProperties.getTimeoutTime());
        LocalDateTime executionTime = timeoutTime.plusMinutes(NumberUtils.INTEGER_ONE);
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Activity activity = new Activity();
        activity.setXid(DTContext.get(DTContextEnum.XID));
        activity.setName(distributedTransactionProperties.getName());
        activity.setStatus(ActivityStatus.INIT.getStatus());
        activity.setStartTime(nowDate);
        activity.setTimeoutTime(Date.from(timeoutTime.atZone(ZoneId.systemDefault()).toInstant()));
        activity.setExecutionTime(Date.from(executionTime.atZone(ZoneId.systemDefault()).toInstant()));
        activity.setRetryCount(NumberUtils.INTEGER_ZERO);
        activity.setGmtCreate(nowDate);
        activity.setGmtModified(nowDate);
        return activity;
    }

    public boolean commit() {
        return dtResourceManager.commitAction();
    }

    public boolean rollback() {
        return dtResourceManager.rollbackAction();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
