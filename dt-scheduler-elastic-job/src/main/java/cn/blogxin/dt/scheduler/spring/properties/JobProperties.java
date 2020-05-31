package cn.blogxin.dt.scheduler.spring.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Job属性配置
 *
 * @author kris
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "dt.job")
public class JobProperties {

    /**
     * Elastic-job zk列表
     */
    private String serverList;

    /**
     * Elastic-job zk节点namespace
     */
    private String namespace = "dtJob";

    /**
     * Elastic-job 任务频率，默认10S执行一次
     */
    private String cron = "0/10 * * * * ?";

    /**
     * Elastic-job 分片数量。默认1，若需要分库分表可以进行自定义
     */
    private int shardingTotalCount = 1;

    /**
     * 任务单个分片单次捞取任务数量
     */
    private int limit = 20;

}
