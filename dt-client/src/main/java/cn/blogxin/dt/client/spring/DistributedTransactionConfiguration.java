package cn.blogxin.dt.client.spring;

import cn.blogxin.dt.client.aop.ActionAutoProxyCreator;
import cn.blogxin.dt.client.aop.ActionRegisterScanner;
import cn.blogxin.dt.client.id.DefaultIdGenerator;
import cn.blogxin.dt.client.id.IdGenerator;
import cn.blogxin.dt.client.log.repository.ActionRepository;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import cn.blogxin.dt.client.tm.LocalTransactionSynchronization;
import cn.blogxin.dt.client.tm.TransactionManager;
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

//    @Bean
//    public ActionInterceptor actionInterceptor(ActionRepository actionRepository) {
//        return new ActionInterceptor(actionRepository);
//    }

    @Bean
    public ActionAutoProxyCreator actionAutoProxyCreator() {
        return new ActionAutoProxyCreator(null);
    }

    @Bean
    public ActionRegisterScanner actionRegisterScanner() {
        return new ActionRegisterScanner();
    }

    @Bean
    public TransactionManager dtTransactionManager() {
        return new TransactionManager();
    }

    @Bean
    public LocalTransactionSynchronization localTransactionSynchronization() {
        return new LocalTransactionSynchronization();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new DefaultIdGenerator();
    }


}
