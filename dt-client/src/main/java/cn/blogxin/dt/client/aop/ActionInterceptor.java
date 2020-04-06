package cn.blogxin.dt.client.aop;

import cn.blogxin.dt.client.annotation.TwoPhaseCommit;
import cn.blogxin.dt.client.context.DTContext;
import cn.blogxin.dt.client.context.DTContextEnum;
import cn.blogxin.dt.client.log.entity.Action;
import cn.blogxin.dt.client.log.entity.ActionStatus;
import cn.blogxin.dt.client.log.repository.ActionRepository;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author kris
 */
public class ActionInterceptor implements MethodInterceptor {

    @Resource
    private ActionRepository actionRepository;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!DTContext.inTransaction()) {
            return invocation.proceed();
        }
        TwoPhaseCommit annotation = invocation.getMethod().getAnnotation(TwoPhaseCommit.class);
        if (annotation != null) {
            String actionName = annotation.name();
            Action action = new Action();
            action.setXid((String) DTContext.get(DTContextEnum.XID));
            action.setName(actionName);
            action.setStatus(ActionStatus.INIT.getStatus());
            Date now = new Date();
            action.setGmtCreate(now);
            action.setGmtModified(now);
            actionRepository.insert(action);
            return invocation.proceed();
        }
        return invocation.proceed();
    }

}
