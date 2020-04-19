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
 * 资源管理器。控制分支事务，负责分支注册、状态汇报，驱动分支事务的提交和回滚。
 *
 * @author kris
 */
@Slf4j
public class ResourceManager {

    private static final Map<String, ActionResource> RESOURCES = Maps.newConcurrentMap();

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private ActivityRepository activityRepository;

    /**
     * 注册Action资源
     *
     * @param resource
     */
    public void registerResource(ActionResource resource) {
        RESOURCES.put(resource.getActionName(), resource);
    }

    /**
     * 注册Action，顺序不能颠倒，必须先添加DTContextEnum.ACTION_CONTEXT上下文，再写库
     *
     * @param action
     */
    public void registerAction(Action action) {
        ActionContext actionMap = DTContext.get(DTContextEnum.ACTION_CONTEXT);
        actionMap.put(action.getName(), action);
        actionRepository.insert(action);
    }

    /**
     * 提交二阶段
     *
     * @return
     */
    public boolean commitAction() {
        boolean result;
        try {
            doAction(ActionStatus.COMMIT);
            activityRepository.updateStatus(DTContext.get(DTContextEnum.XID), ActivityStatus.COMMIT.getStatus(), ActivityStatus.COMMIT_FINISH.getStatus());
            result = true;
            log.info("执行分布式事务二阶段提交成功。xid={}", (String) DTContext.get(DTContextEnum.XID));
        } catch (Exception e) {
            result = false;
            log.error("执行分布式事务二阶段提交失败，等待重试。xid={}", (String) DTContext.get(DTContextEnum.XID), e);
        }
        return result;
    }

    /**
     * 回滚二阶段
     *
     * @return
     */
    public boolean rollbackAction() {
        boolean result;
        try {
            doAction(ActionStatus.ROLLBACK);
            activityRepository.updateStatus(DTContext.get(DTContextEnum.XID), ActivityStatus.INIT.getStatus(), ActivityStatus.ROLLBACK.getStatus());
            result = true;
            log.info("执行分布式事务二阶段回滚成功。xid={}", (String) DTContext.get(DTContextEnum.XID));
        } catch (Exception e) {
            result = false;
            log.error("执行分布式事务二阶段回滚失败，等待重试。xid={}", (String) DTContext.get(DTContextEnum.XID), e);
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
                    actionRepository.updateStatus(action.getXid(), action.getName(), action.getStatus(), actionStatus.getStatus());
                }
            }
        } catch (Exception e) {
            throw new DTException("执行Action二阶段失败", e);
        }
    }

}
