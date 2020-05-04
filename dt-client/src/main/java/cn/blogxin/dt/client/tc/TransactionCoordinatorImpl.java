package cn.blogxin.dt.client.tc;

import cn.blogxin.dt.client.context.ActionContext;
import cn.blogxin.dt.client.context.DTContext;
import cn.blogxin.dt.client.context.DTContextEnum;
import cn.blogxin.dt.client.log.entity.Action;
import cn.blogxin.dt.client.log.entity.Activity;
import cn.blogxin.dt.client.log.enums.ActionStatus;
import cn.blogxin.dt.client.log.enums.ActivityStatus;
import cn.blogxin.dt.client.log.repository.ActionRepository;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import cn.blogxin.dt.client.rm.ResourceManager;
import cn.blogxin.dt.client.util.Utils;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author kris
 */
public class TransactionCoordinatorImpl implements TransactionCoordinator {

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private ResourceManager dtResourceManager;

    @Override
    public void batchReTry(CoordinatorParam param) {
        List<Activity> activities = activityRepository.queryUnfinished(param.getShardingKey(), new Date(), param.getLimit());
        for (Activity activity : activities) {
            if (!execute(activity)) {
                activityRepository.updateRetry(activity.getXid(), activity.getStatus(), activity.getRetryCount() + 1, getRetryTime(activity.getExecutionTime()));
            }
        }
    }

    /**
     * 执行单个Activity补偿操作
     *
     * @param activity
     * @return
     */
    private boolean execute(Activity activity) {
        boolean result = false;
        ActivityStatus activityStatus = Utils.getFinalStatus(activity);
        if (activityStatus == null) {
            return false;
        }
        try {
            Utils.initDTContext(activity);
            ActionContext actionContext = DTContext.get(DTContextEnum.ACTION_CONTEXT);
            List<Action> actions = actionRepository.query(activity.getXid());
            for (Action action : actions) {
                if (ActionStatus.INIT.getStatus() == action.getStatus()) {
                    actionContext.put(action.getName(), action);
                }
            }
            if (ActivityStatus.COMMIT_FINISH == activityStatus) {
                result = dtResourceManager.commitAction();
            } else if (ActivityStatus.ROLLBACK == activityStatus) {
                result = dtResourceManager.rollbackAction();
            }
        } finally {
            DTContext.clear();
        }
        return result;
    }

    private Date getRetryTime(Date executionTime) {
        Instant instant = executionTime.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime tmp = instant.atZone(zoneId).toLocalDateTime();
        LocalDateTime retryTime = tmp.plusMinutes(5);
        return Date.from(retryTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
