package cn.blogxin.dt.scheduler.spring;

import cn.blogxin.dt.client.constant.Constant;
import cn.blogxin.dt.client.spring.DistributedTransactionConfiguration;
import cn.blogxin.dt.client.tc.TransactionCoordinator;
import cn.blogxin.dt.scheduler.CoordinatorJob;
import cn.blogxin.dt.scheduler.spring.properties.JobProperties;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Configuration
@AutoConfigureAfter(DistributedTransactionConfiguration.class)
@ConditionalOnClass(TransactionCoordinator.class)
@EnableConfigurationProperties(JobProperties.class)
public class SchedulerConfiguration {

    @Resource
    private JobProperties jobProperties;

    @Bean
    public CoordinatorJob coordinatorJob() {
        return new CoordinatorJob();
    }

    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter dtRegCenter() {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(jobProperties.getServerList(), Constant.DT_COORDINATOR_JOB_BASE_NAMESPACE + jobProperties.getNamespace()));
    }

    @Bean
    public LiteJobConfiguration dtLiteJobConfiguration(CoordinatorJob coordinatorJob) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(coordinatorJob.getClass().getName(), jobProperties.getCron(), jobProperties.getShardingTotalCount())
                .description("DT分布式事务协调器任务").build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, coordinatorJob.getClass().getCanonicalName());
        return LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();
    }

    @Bean(initMethod = "init")
    public JobScheduler dtScheduler(CoordinatorRegistryCenter dtRegCenter, LiteJobConfiguration dtLiteJobConfiguration, CoordinatorJob coordinatorJob) {
        return new SpringJobScheduler(coordinatorJob, dtRegCenter, dtLiteJobConfiguration);
    }

}
