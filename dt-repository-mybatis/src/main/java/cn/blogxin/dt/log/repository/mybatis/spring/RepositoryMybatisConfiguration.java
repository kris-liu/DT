package cn.blogxin.dt.log.repository.mybatis.spring;

import cn.blogxin.dt.client.log.repository.ActionRepository;
import cn.blogxin.dt.client.log.repository.ActivityRepository;
import cn.blogxin.dt.log.repository.mybatis.ActionMybatisRepository;
import cn.blogxin.dt.log.repository.mybatis.ActivityMybatisRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kris
 */
@Configuration
@ConditionalOnProperty(prefix = "dt", name = "enable", havingValue = "true")
public class RepositoryMybatisConfiguration {

    @Bean
    public ActivityRepository activityRepository() {
        return new ActivityMybatisRepository();
    }

    @Bean
    public ActionRepository actionRepository() {
        return new ActionMybatisRepository();
    }

}
