# springcloud-service-consumer

## springcloud-service-consumer 工程概述
这个工程是是一个微服务消费者工程，负责消费服务提供者（springcloud-service-provider-high-available）提供的服务，该工程提供的服务与服务提供者提供的服务一一对应。
同时，该工程基于`Ribbon`实现了客户端负载均衡。

## 实现步骤分析
### 引入相关依赖依赖

```
<dependencies>
	<!-- SpringBoot依赖 -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<!-- Eureka Client依赖 -->
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
	</dependency>
	<!-- ribbon客户端负载均衡依赖 -->
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-ribbon</artifactId>
	</dependency>
</dependencies>
```
### 创建application.yml文件
```
server:
  port: 9000

eureka:
  client:
    register-with-eureka: false
    service-url: 
      # eureka Server端地址
      defaultZone: http://peer1:8761/eureka/,http://peer2:8762/eureka/     
```

### 创建springcloud-service-consumer启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // 表示该工程是一个Eureka客户端
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
```
### 服务消费示例代码
```
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController // @RestController <==> @Controller + @ResponseBody
public class ServiceProviderConsumerController {

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * 这个前缀是服务提供者（springcloud-service-provider）注册到EurkaServer中的服务名称
	 */
	private static final String REST_URL_PREFIX = "http://MICROSERVICE-SERVICE-PROVIDER";

	@RequestMapping("/consumer/service1")
	public String service1() {
		/*
		 * 通过restful实现对服务提供者所提供方法的调用
		 */
		String url = REST_URL_PREFIX + "/provider/service1";
		return restTemplate.postForObject(url, null, String.class);
	}

	@RequestMapping("/consumer/service/discovery")
	public Object discovery() {
		String url = REST_URL_PREFIX + "/service/discovery";
		return restTemplate.postForObject(url, null, Object.class);
	}

	// 获取请求URL
	@RequestMapping("/consumer/request/info")
	public String requestInfo(HttpServletRequest request) {
		String url = REST_URL_PREFIX + "/provider/request/info";
		return restTemplate.postForObject(url, null, String.class);
	}

}

```
服务消费端提供的方法与服务提供者`springcloud-service-provider-high-available`暴露的方法一致。
服务消费端通过	`restfull`直接请求服务生产端所提供的方法，

### 编写配置类
```
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
```
通过`@Bean`注解注入`RestTemplate`对象，同时使用`@LoadBalanced`实现客户端负载均衡。

### 测试
启动本项目后，通过浏览器内反复访问`http://localhost:9000/consumer/request/info`，该请求返回的是`http`请求相关的信息，对应着服务端的相应方法。



注意事项：
- 启动本示例之前，需要提前启动`Eureka Server`高可用工程（`springcloud-eureka-server-peer`）和服务提供者高可用工程（`springcloud-service-provider-high-available`）
- `Ribbon`默认的负载均衡策略是轮询。



博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[基于SpringCloud完成服务提供者示例](https://blog.csdn.net/myNameIssls/article/details/81193557)

参考链接：
[https://cloud.spring.io/spring-cloud-static/Finchley.SR1/single/spring-cloud.html#spring-cloud-ribbon](https://cloud.spring.io/spring-cloud-static/Finchley.SR1/single/spring-cloud.html#spring-cloud-ribbon)











