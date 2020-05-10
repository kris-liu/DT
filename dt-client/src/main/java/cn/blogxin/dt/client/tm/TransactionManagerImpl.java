package cn.blogxin.dt.client.tm;

import cn.blogxin.dt.client.constant.Constant;
import cn.blogxin.dt.client.context.DTContext;
import cn.blogxin.dt.client.context.DTContextEnum;
import cn.blogxin.dt.client.exception.DTException;
import cn.blogxin.dt.client.id.IdGenerator;
import cn.blogxin.dt.client.log.entity.Activity;
import cn.blogxin.dt.client.log.enums.ActivityStatus;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import cn.blogxin.dt.client.rm.ResourceManager;
import cn.blogxin.dt.client.spring.properties.DistributedTransactionProperties;
import cn.blogxin.dt.client.util.Utils;
import lombok.extern.slf4j.Slf4j;
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
 * @author kris
 */
@Slf4j
public class TransactionManagerImpl implements TransactionManager, ApplicationContextAware {

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

    @Override
    public void start(String xidSuffix) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new DTException("分布式事务需要在一个本地事务环境中开启");
        }
        try {
            String xid = idGenerator.getId(xidSuffix);
            Activity activity = initActivity(xid);
            Utils.initDTContext(activity);
            activityRepository.insert(activity);
            activityRepository.updateStatus(activity.getXid(), ActivityStatus.INIT, ActivityStatus.COMMIT);
        } finally {
            TransactionSynchronizationManager.registerSynchronization(twoPhaseTransactionSynchronization);
        }
    }

    private Activity initActivity(String xid) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeoutTime = now.plusSeconds(distributedTransactionProperties.getTimeoutTime());
        LocalDateTime executionTime = timeoutTime.plusSeconds(Constant.TIMEOUT_RETRY_EXECUTION_TIME);
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Activity activity = new Activity();
        activity.setXid(xid);
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

    @Override
    public boolean commit() {
        try {
            boolean commitAction = dtResourceManager.commitAction();
            if (commitAction) {
                activityRepository.updateStatus(DTContext.get(DTContextEnum.XID), ActivityStatus.COMMIT, ActivityStatus.COMMIT_FINISH);
                log.info("执行分布式事务二阶段提交成功。xid={}", (String) DTContext.get(DTContextEnum.XID));
                return true;
            }
        } catch (Exception e) {
            log.error("执行分布式事务二阶段提交异常，等待重试。xid={}", DTContext.get(DTContextEnum.XID), e);
            return false;
        }
        log.warn("执行分布式事务二阶段提交失败，等待重试。xid={}", (String) DTContext.get(DTContextEnum.XID));
        return false;
    }

    @Override
    public boolean rollback() {
        try {
            boolean rollbackAction = dtResourceManager.rollbackAction();
            if (rollbackAction) {
                activityRepository.updateStatus(DTContext.get(DTContextEnum.XID), ActivityStatus.INIT, ActivityStatus.ROLLBACK);
                log.info("执行分布式事务二阶段回滚成功。xid={}", (String) DTContext.get(DTContextEnum.XID));
                return true;
            }
        } catch (Exception e) {
            log.error("执行分布式事务二阶段回滚异常，等待重试。xid={}", DTContext.get(DTContextEnum.XID), e);
            return false;
        }
        log.warn("执行分布式事务二阶段回滚失败，等待重试。xid={}", (String) DTContext.get(DTContextEnum.XID));
        return false;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
