package cn.tyrone.springcloud.service.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // 用于标记这个类属于配置类
public class BeanConfig {

	@Bean // @Bean相当于配置文件中<bean id = "restTemplate" class="org.springframework.web.client.RestTemplate" />
	@LoadBalanced // 实现基于客户端的负载均衡
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
}
