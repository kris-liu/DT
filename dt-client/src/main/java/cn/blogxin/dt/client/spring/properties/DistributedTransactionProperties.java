package cn.blogxin.dt.client.spring.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kris
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "dt")
public class DistributedTransactionProperties {

    /**
     * 分布式事务是否启动
     */
    private boolean enable = false;

    /**
     * 分布式事务名称
     */
    private String name;

    /**
     * 分布式任务超时时间，默认30S
     */
    private long timeoutTime = 30;

    /**
     * ID生成器
     */
    private String idGeneratorClass;

}
