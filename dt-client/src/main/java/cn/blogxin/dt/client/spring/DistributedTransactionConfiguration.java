package cn.blogxin.dt.client.spring;

import cn.blogxin.dt.client.constant.Constant;
import cn.blogxin.dt.client.dubbo.DubboActionRegisterApplicationListener;
import cn.blogxin.dt.client.id.DefaultIdGenerator;
import cn.blogxin.dt.client.id.IdGenerator;
import cn.blogxin.dt.client.job.CoordinatorJob;
import cn.blogxin.dt.client.log.repository.ActionRepository;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import cn.blogxin.dt.client.log.repository.mybatis.ActionMybatisRepository;
import cn.blogxin.dt.client.log.repository.mybatis.ActivityMybatisRepository;
import cn.blogxin.dt.client.rm.ResourceManager;
import cn.blogxin.dt.client.rm.ResourceManagerImpl;
import cn.blogxin.dt.client.tc.TransactionCoordinator;
import cn.blogxin.dt.client.tc.TransactionCoordinatorImpl;
import cn.blogxin.dt.client.tm.TransactionManager;
import cn.blogxin.dt.client.tm.TransactionManagerImpl;
import cn.blogxin.dt.client.tm.TwoPhaseTransactionSynchronization;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
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
        return new ActivityMybatisRepository();
    }

    @Bean
    public ActionRepository actionRepository() {
        return new ActionMybatisRepository();
    }

    @Bean
    @ConditionalOnClass(ReferenceAnnotationBeanPostProcessor.class)
    public DubboActionRegisterApplicationListener dubboActionRegisterApplicationListener() {
        return new DubboActionRegisterApplicationListener();
    }

    @Bean
    public TransactionManager dtTransactionManager() {
        return new TransactionManagerImpl();
    }

    @Bean
    public ResourceManager dtResourceManager() {
        return new ResourceManagerImpl();
    }

    @Bean
    public TwoPhaseTransactionSynchronization twoPhaseTransactionSynchronization() {
        return new TwoPhaseTransactionSynchronization();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new DefaultIdGenerator();
    }

    @Bean
    public TransactionCoordinator transactionCoordinator() {
        return new TransactionCoordinatorImpl();
    }

    @Bean
    public CoordinatorJob coordinatorJob() {
        return new CoordinatorJob();
    }

    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter dtRegCenter() {
        JobProperties job = distributedTransactionProperties.getJob();
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(job.getServerList(), Constant.DT_COORDINATOR_JOB_BASE_NAMESPACE + job.getNamespace()));
    }

    @Bean
    public LiteJobConfiguration dtLiteJobConfiguration(CoordinatorJob coordinatorJob) {
        JobProperties job = distributedTransactionProperties.getJob();
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(coordinatorJob.getClass().getName(), job.getCron(), job.getShardingTotalCount())
                .description("DT分布式事务协调器任务").build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, coordinatorJob.getClass().getCanonicalName());
        return LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();
    }

    @Bean(initMethod = "init")
    public JobScheduler coordinatorJob(CoordinatorRegistryCenter dtRegCenter, LiteJobConfiguration dtLiteJobConfiguration, CoordinatorJob coordinatorJob) {
        return new SpringJobScheduler(coordinatorJob, dtRegCenter, dtLiteJobConfiguration);
    }
}
