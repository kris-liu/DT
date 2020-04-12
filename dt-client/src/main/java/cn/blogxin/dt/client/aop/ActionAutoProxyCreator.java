package cn.blogxin.dt.client.aop;

import cn.blogxin.dt.client.annotation.TwoPhaseCommit;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * @author kris
 */
public class ActionAutoProxyCreator extends AbstractAutoProxyCreator {

    private Object[] interceptors = new Object[]{new ActionInterceptor()};


    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        return super.wrapIfNecessary(bean, beanName, cacheKey);
    }

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, @Nullable TargetSource customTargetSource) throws BeansException {
        Method[] methods = beanClass.getClass().getDeclaredMethods();
        for (Method method : methods) {
            TwoPhaseCommit annotation = method.getAnnotation(TwoPhaseCommit.class);
            if (annotation != null) {
                return interceptors;
            }
        }
        return new Object[0];
    }
}
