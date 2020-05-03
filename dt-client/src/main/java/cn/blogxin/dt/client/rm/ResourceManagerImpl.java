package cn.blogxin.dt.client.rm;

import cn.blogxin.dt.client.context.ActionContext;
import cn.blogxin.dt.client.context.DTContext;
import cn.blogxin.dt.client.context.DTContextEnum;
import cn.blogxin.dt.client.context.DTParam;
import cn.blogxin.dt.client.exception.DTException;
import cn.blogxin.dt.client.log.entity.Action;
import cn.blogxin.dt.client.log.enums.ActionStatus;
import cn.blogxin.dt.client.log.enums.ActivityStatus;
import cn.blogxin.dt.client.log.repository.ActionRepository;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import cn.blogxin.dt.client.util.Utils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author kris
 */
@Slf4j
public class ResourceManagerImpl implements ResourceManager {

    private static final Map<String, ActionResource> RESOURCES = Maps.newConcurrentMap();

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private ActivityRepository activityRepository;

    @Override
    public void registerResource(ActionResource resource) {
        RESOURCES.put(resource.getActionName(), resource);
    }

    @Override
    public void registerAction(Action action) {
        ActionContext actionContext = DTContext.get(DTContextEnum.ACTION_CONTEXT);
        actionContext.put(action.getName(), action);
        actionRepository.insert(action);
    }

    @Override
    public boolean commitAction() {
        boolean result;
        try {
            doAction(ActionStatus.COMMIT);
            activityRepository.updateStatus(DTContext.get(DTContextEnum.XID), ActivityStatus.COMMIT, ActivityStatus.COMMIT_FINISH);
            result = true;
            log.info("执行分布式事务二阶段提交成功。xid={}", (String) DTContext.get(DTContextEnum.XID));
        } catch (Exception e) {
            result = false;
            log.error("执行分布式事务二阶段提交失败，等待重试。xid={}", DTContext.get(DTContextEnum.XID), e);
        }
        return result;
    }

    @Override
    public boolean rollbackAction() {
        boolean result;
        try {
            doAction(ActionStatus.ROLLBACK);
            activityRepository.updateStatus(DTContext.get(DTContextEnum.XID), ActivityStatus.INIT, ActivityStatus.ROLLBACK);
            result = true;
            log.info("执行分布式事务二阶段回滚成功。xid={}", (String) DTContext.get(DTContextEnum.XID));
        } catch (Exception e) {
            result = false;
            log.error("执行分布式事务二阶段回滚失败，等待重试。xid={}", DTContext.get(DTContextEnum.XID), e);
        }
        return result;
    }

    private void doAction(ActionStatus actionStatus) {
        try {
            ActionContext actionContext = DTContext.get(DTContextEnum.ACTION_CONTEXT);
            Map<String, Action> actionMap = actionContext.getActionMap();
            if (MapUtils.isNotEmpty(actionMap)) {
                for (Map.Entry<String, Action> entry : actionMap.entrySet()) {
                    ActionResource actionResource = RESOURCES.get(entry.getKey());
                    Action action = entry.getValue();
                    DTParam dtParam = new DTParam();
                    dtParam.setXid(DTContext.get(DTContextEnum.XID));
                    dtParam.setStartTime(DTContext.get(DTContextEnum.START_TIME));
                    Method twoPhaseMethod = Utils.getTwoPhaseMethodByActionStatus(actionResource, actionStatus);
                    Object[] args = Utils.getTwoPhaseMethodParam(twoPhaseMethod, action.getArguments(), dtParam);
                    twoPhaseMethod.invoke(actionResource.getActionBean(), args);
                    actionRepository.updateStatus(action.getXid(), action.getName(), ActionStatus.INIT, actionStatus);
                }
            }
        } catch (Exception e) {
            throw new DTException("执行Action二阶段失败", e);
        }
    }

}
