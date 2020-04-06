package cn.blogxin.dt.client.aop;

import cn.blogxin.dt.client.annotation.TwoPhaseCommit;
import cn.blogxin.dt.client.rm.ResourceManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * 分支事务扫描器
 * 扫描所有分支事务，即带有@TwoPhaseCommit注解的一组方法，注册到RM。
 *
 * @author kris
 */
public class ActionScanner implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            TwoPhaseCommit annotation = method.getAnnotation(TwoPhaseCommit.class);
            if (annotation != null) {
                ActionResource resource = new ActionResource();
                ResourceManager.registerResource(resource);
            }
        }
        return bean;
    }
}
