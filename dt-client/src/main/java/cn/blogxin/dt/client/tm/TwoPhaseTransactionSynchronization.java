package cn.blogxin.dt.client.tm;

import cn.blogxin.dt.client.context.DTContext;
import cn.blogxin.dt.client.context.DTContextEnum;
import cn.blogxin.dt.client.exception.DTException;
import cn.blogxin.dt.client.log.entity.Activity;
import cn.blogxin.dt.client.log.enums.ActivityStatus;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import org.springframework.transaction.support.TransactionSynchronization;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 本地事务同步器。
 * 用于在本地事务提交或回滚后执行分布式事务整体的提交或回滚
 *
 * @author kris
 */
public class TwoPhaseTransactionSynchronization implements TransactionSynchronization {

    @Resource
    private TransactionManager dtTransactionManager;

    @Resource
    private ActivityRepository activityRepository;

  @Override
    public void beforeCommit(boolean readOnly) {
        if (!DTContext.inTransaction()) {
            return;
        }
        Activity activity = DTContext.get(DTContextEnum.ACTIVITY);
        if (activity.getTimeoutTime().before(new Date())) {
            throw new DTException("分布式事务提交超时");
        }
    }

    @Override
    public void afterCompletion(int status) {
        if (!DTContext.inTransaction()) {
            return;
        }
        try {
            Activity activity = activityRepository.queryForUpdate(DTContext.get(DTContextEnum.XID));
            if (status == TransactionSynchronization.STATUS_COMMITTED && ActivityStatus.COMMIT.getStatus() == activity.getStatus()) {
                dtTransactionManager.commit();
            } else if (status == TransactionSynchronization.STATUS_ROLLED_BACK && ActivityStatus.INIT.getStatus() == activity.getStatus()) {
                dtTransactionManager.rollback();
            }
        } finally {
            DTContext.clear();
        }
    }

}
