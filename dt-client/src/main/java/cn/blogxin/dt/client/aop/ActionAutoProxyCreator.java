package cn.blogxin.dt.client.aop;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * @author kris
 */
public class ActionAutoProxyCreator extends AbstractAutoProxyCreator {

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> aClass, String s, @Nullable TargetSource targetSource) throws BeansException {
        return new Object[0];
    }

}
