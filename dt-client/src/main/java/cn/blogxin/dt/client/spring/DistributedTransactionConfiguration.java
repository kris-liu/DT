package cn.blogxin.dt.client.spring;

import cn.blogxin.dt.client.dubbo.DubboActionRegisterApplicationListener;
import cn.blogxin.dt.client.id.DefaultIdGenerator;
import cn.blogxin.dt.client.id.IdGenerator;
import cn.blogxin.dt.client.log.repository.ActionRepository;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import cn.blogxin.dt.client.rm.ResourceManager;
import cn.blogxin.dt.client.tm.TransactionManager;
import cn.blogxin.dt.client.tm.TwoPhaseTransactionSynchronization;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Configuration
@ConditionalOnProperty(prefix = "dt", name = "enable", havingValue = "true")
@EnableConfigurationProperties(DistributedTransactionProperties.class)
public class DistributedTransactionConfiguration {

    @Resource
    private DistributedTransactionProperties distributedTransactionProperties;


    @Bean
    public ActivityRepository activityRepository() {
        return new ActivityRepository();
    }

    @Bean
    public ActionRepository actionRepository() {
        return new ActionRepository();
    }

    @Bean
    @ConditionalOnClass(ReferenceAnnotationBeanPostProcessor.class)
    public DubboActionRegisterApplicationListener dubboActionRegisterApplicationListener() {
        return new DubboActionRegisterApplicationListener();
    }
//    @Bean
//    public ActionInterceptor actionInterceptor() {
//        return new ActionInterceptor();
//    }

//    @Bean
//    public ActionAutoProxyCreator actionAutoProxyCreator() {
//        return new ActionAutoProxyCreator();
//    }

//    @Bean
//    public ActionRegisterScanner actionRegisterScanner() {
//        return new ActionRegisterScanner();
//    }

    @Bean
    public TransactionManager dtTransactionManager() {
        return new TransactionManager();
    }

    @Bean
    public ResourceManager dtResourceManager() {
        return new ResourceManager();
    }

    @Bean
    public TwoPhaseTransactionSynchronization twoPhaseTransactionSynchronization() {
        return new TwoPhaseTransactionSynchronization();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new DefaultIdGenerator();
    }


}
