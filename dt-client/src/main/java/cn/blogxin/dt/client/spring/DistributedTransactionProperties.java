package cn.blogxin.dt.client.spring;

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

    private String name;

    private long timeoutTime;


//    private String name;
//    private String name;


}
