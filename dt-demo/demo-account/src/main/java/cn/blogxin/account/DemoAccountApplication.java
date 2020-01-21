package cn.blogxin.account;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xin
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "cn.blogxin.account.dubbo")
public class DemoAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAccountApplication.class, args);
	}

}
