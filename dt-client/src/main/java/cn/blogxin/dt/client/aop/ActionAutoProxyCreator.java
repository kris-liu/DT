//package cn.blogxin.dt.client.aop;
//
//import cn.blogxin.dt.client.annotation.TwoPhaseCommit;
//import org.springframework.aop.TargetSource;
//import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
//import org.springframework.beans.BeansException;
//import org.springframework.core.Ordered;
//import org.springframework.lang.Nullable;
//
//import javax.annotation.Resource;
//import java.lang.reflect.Method;
//
///**
// * @author kris
// */
//public class ActionAutoProxyCreator extends AbstractAutoProxyCreator {
//
//    private Object[] interceptors;
//
//    @Resource
//    private ActionInterceptor actionInterceptor;
//
//    public ActionAutoProxyCreator() {
//        setOrder(Ordered.LOWEST_PRECEDENCE);
//        setProxyTargetClass(true);
//        this.interceptors = new Object[]{actionInterceptor};
//    }
//
//    @Override
//    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
//        return super.wrapIfNecessary(bean, beanName, cacheKey);
//    }
//
//    @Override
//    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, @Nullable TargetSource customTargetSource) throws BeansException {
//        Method[] methods = beanClass.getClass().getDeclaredMethods();
//        for (Method method : methods) {
//            TwoPhaseCommit annotation = method.getAnnotation(TwoPhaseCommit.class);
//            if (annotation != null) {
//                return interceptors;
//            }
//        }
//        return null;
//    }
//
//}
