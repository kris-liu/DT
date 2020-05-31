package cn.blogxin.dt.client.spring;

import cn.blogxin.dt.client.id.DefaultIdGenerator;
import cn.blogxin.dt.client.id.IdGenerator;
import cn.blogxin.dt.client.rm.ResourceManager;
import cn.blogxin.dt.client.rm.ResourceManagerImpl;
import cn.blogxin.dt.client.spring.properties.DistributedTransactionProperties;
import cn.blogxin.dt.client.tc.TransactionCoordinator;
import cn.blogxin.dt.client.tc.TransactionCoordinatorImpl;
import cn.blogxin.dt.client.tm.TransactionManager;
import cn.blogxin.dt.client.tm.TransactionManagerImpl;
import cn.blogxin.dt.client.tm.TwoPhaseTransactionSynchronization;
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
    public TransactionManager dtTransactionManager() {
        return new TransactionManagerImpl();
    }

    @Bean
    public ResourceManager dtResourceManager() {
        return new ResourceManagerImpl();
    }

    @Bean
    public TransactionCoordinator transactionCoordinator() {
        return new TransactionCoordinatorImpl();
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
