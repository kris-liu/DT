package cn.blogxin.dt.client.dubbo;

import cn.blogxin.dt.client.annotation.TwoPhaseCommit;
import cn.blogxin.dt.client.context.DTContext;
import cn.blogxin.dt.client.context.DTContextEnum;
import cn.blogxin.dt.client.exception.DTException;
import cn.blogxin.dt.client.log.entity.Action;
import cn.blogxin.dt.client.log.enums.ActionStatus;
import cn.blogxin.dt.client.rm.ResourceManager;
import cn.blogxin.dt.client.util.Utils;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.extension.ExtensionFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.*;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author kris
 */
@Slf4j
@Activate(group = CommonConstants.CONSUMER, order = Integer.MIN_VALUE)
public class ActionFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (!DTContext.inTransaction()) {
            return invoker.invoke(invocation);
        }
        try {
            String serviceName = invocation.getServiceName();
            Class<?> aClass = Class.forName(serviceName, true, Thread.currentThread().getContextClassLoader());
            Method method = aClass.getDeclaredMethod(invocation.getMethodName(), invocation.getParameterTypes());
            TwoPhaseCommit annotation = method.getAnnotation(TwoPhaseCommit.class);
            if (annotation != null) {
                Date now = new Date();
                Action action = new Action();
                action.setXid(DTContext.get(DTContextEnum.XID));
                action.setName(Utils.getActionName(aClass, method, annotation));
                action.setStatus(ActionStatus.INIT.getStatus());
                action.setArguments(JSONArray.toJSONString(invocation.getArguments()));
                action.setGmtCreate(now);
                action.setGmtModified(now);
                ExtensionFactory objectFactory = ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension();
                ResourceManager resourceManager = objectFactory.getExtension(ResourceManager.class, "dtResourceManager");
                resourceManager.registerAction(action);
                //todo test
//                if (serviceName.contains("Coupon")) {
//                    int i = 10/0;
//                }
            }
        } catch (Exception e) {
            log.error("ActionFilter error", e);
            throw new DTException("插入分支事务失败");
        }
        return invoker.invoke(invocation);
    }

}
