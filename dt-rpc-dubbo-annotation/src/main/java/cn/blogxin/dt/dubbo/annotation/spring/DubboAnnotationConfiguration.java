package cn.blogxin.dt.dubbo.annotation.spring;

import cn.blogxin.dt.dubbo.annotation.DubboActionRegisterApplicationListener;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用时机：通过@Reference注解使用dubbo时
 * @author kris
 */
@Configuration
@ConditionalOnProperty(prefix = "dt", name = "enable", havingValue = "true")
public class DubboAnnotationConfiguration {

    @Bean
    @ConditionalOnClass(ReferenceAnnotationBeanPostProcessor.class)
    public DubboActionRegisterApplicationListener dubboActionRegisterApplicationListener() {
        return new DubboActionRegisterApplicationListener();
    }

}
