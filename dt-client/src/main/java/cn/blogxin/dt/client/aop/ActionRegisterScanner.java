package cn.blogxin.dt.client.aop;

import cn.blogxin.dt.client.annotation.TwoPhaseCommit;
import cn.blogxin.dt.client.rm.ActionResource;
import cn.blogxin.dt.client.rm.ResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * 分支事务扫描器
 * 扫描所有分支事务，即带有@TwoPhaseCommit注解的一组方法，注册到RM。
 *
 * @author kris
 */
@Slf4j
public class ActionRegisterScanner implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        ConfigurableBeanFactory applicationContext = (ConfigurableBeanFactory) this.applicationContext.getAutowireCapableBeanFactory();
//        String[] singletonNames = applicationContext.getSingletonNames();
//        for (String name : singletonNames) {
//            if (name.startsWith("@R")) {
//                System.out.println(name);
//                Object aThisapplicationContextBean = this.applicationContext.getBean(name);
//                Object initialization = this.applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(aThisapplicationContextBean, name);
//                System.out.println(initialization);
//            }
//        }


        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            TwoPhaseCommit annotation = method.getAnnotation(TwoPhaseCommit.class);
            if (annotation != null) {
                ActionResource resource = new ActionResource();
                resource.setActionBean(annotation.name());
                resource.setActionBean(bean);
                resource.setTryMethod(method.getName());
                resource.setConfirmMethod(annotation.confirmMethod());
                resource.setCancelMethod(annotation.cancelMethod());
                ResourceManager.registerResource(resource);
            }
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
