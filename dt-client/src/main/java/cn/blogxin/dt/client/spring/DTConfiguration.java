package cn.blogxin.dt.client.spring;

import cn.blogxin.dt.client.id.DefaultIdGenerator;
import cn.blogxin.dt.client.id.IdGenerator;
import cn.blogxin.dt.client.tm.TransactionManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author kris
 */
@Configuration
@EnableConfigurationProperties(DTProperties.class)
public class DTConfiguration {

    @Resource
    private DTProperties dtProperties;

    @Bean
    private TransactionManager transactionManager() {
        return new TransactionManager();
    }

    @Bean
    private IdGenerator idGenerator() {
        return new DefaultIdGenerator();
    }


}
