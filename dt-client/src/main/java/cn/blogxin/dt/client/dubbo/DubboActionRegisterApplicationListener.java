package cn.blogxin.dt.client.dubbo;

import cn.blogxin.dt.client.annotation.TwoPhaseCommit;
import cn.blogxin.dt.client.rm.ActionResource;
import cn.blogxin.dt.client.rm.ResourceManager;
import cn.blogxin.dt.client.util.Utils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 注册Dubbo类型的Action
 * dubbo的@Reference注解引入的依赖无法在BeanPostProcessor获取到，需要通过监听器在容器启动前获取并注册
 * @author kris
 */
public class DubboActionRegisterApplicationListener implements ApplicationListener, Ordered {

    @Resource
    private ResourceManager dtResourceManager;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            onContextRefreshedEvent((ContextRefreshedEvent) event);
        }
    }

    private void onContextRefreshedEvent(ContextRefreshedEvent event) {
        ReferenceAnnotationBeanPostProcessor referenceAnnotationBeanPostProcessor = event.getApplicationContext().getBean(ReferenceAnnotationBeanPostProcessor.class);
        Collection<ReferenceBean<?>> referenceBeans = referenceAnnotationBeanPostProcessor.getReferenceBeans();
        if (CollectionUtils.isEmpty(referenceBeans)) {
            return;
        }
        for (ReferenceBean<?> referenceBean : referenceBeans) {
            Class<?> interfaceClass = referenceBean.getInterfaceClass();
            Method[] methods = interfaceClass.getDeclaredMethods();
            for (Method method : methods) {
                TwoPhaseCommit annotation = method.getAnnotation(TwoPhaseCommit.class);
                if (annotation != null) {
                    ActionResource resource = new ActionResource();
                    resource.setActionName(Utils.getActionName(interfaceClass, method, annotation));
                    resource.setActionBean(referenceBean.getObject());
                    resource.setTryMethod(method);
                    resource.setConfirmMethod(Utils.getTwoPhaseMethodByName(annotation.confirmMethod(), methods));
                    resource.setCancelMethod(Utils.getTwoPhaseMethodByName(annotation.cancelMethod(), methods));
                    dtResourceManager.registerResource(resource);
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
