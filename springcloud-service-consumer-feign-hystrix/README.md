# springcloud-service-consumer-feign-hystrix

## springcloud-service-consumer-feign-hystrix 工程概述
该工程通过feign实现服务消费者，并开启了hystrix熔断机制确保服务提供者在服务异常的情况下，可以将服务请求转发至相应的服务异常处理方法中，以确保整个服务过程不会被阻塞。

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
	<!-- Feign 依赖 -->
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-openfeign</artifactId>
	</dependency>
</dependencies>
```
### 创建application.yml文件
```
server:
  port: 9000 # 指定工程端口号

feign: 
  hystrix: 
    enabled: true # 启动hystrix熔断机制
eureka: 
  client:
    register-with-eureka: false
    service-url: 
      # eureka Server端地址
      defaultZone: http://peer1:8761/eureka/,http://peer2:8762/eureka/
```

### 创建springcloud-service-consumer-feign-hystrix启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients // 启动Feign
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
```
### 服务消费示例代码

#### 消费端Controller代码

```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tyrone.springcloud.service.consumer.feign.hystrix.service.ServiceProviderConsumerFeignService;

/**
 * 服务消费端
 * 这是服务消费端暴露给客户端请求的地址
 * @author shanglishuai
 *
 */
@RestController // @RestController <==> @Controller + @ResponseBody
public class ServiceProviderConsumerController {

	// 注入Feign接口
	@Autowired
	private ServiceProviderConsumerFeignService feignService;

	/**
	 * 服务消费端方法
	 * 该方法通过与@FeignClient("MICROSERVICE-SERVICE-PROVIDER")标记的接口实现与服务提供者的交互
	 * @return
	 */
	@RequestMapping("/consumer/request/info")
	public String requestInfo(boolean flag) {
		return feignService.requestInfo();
	}

}

```

#### FeignClient接口代码
```
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @FeignClient("MICROSERVICE-SERVICE-PROVIDER")
 * MICROSERVICE-SERVICE-PROVIDER:是一个服务提供者的实例名称
 * 用于标记一个可用的服务的名称，同时创建一个Ribbon负载均衡器
 * fallbackFactory指定用于处理服务提供者服务异常过程的类
 * 该接口用于实现与服务提供者进行交互，接口中的方法与服务提供者需要暴露的方法一一对应
 */
@FeignClient(value = "MICROSERVICE-SERVICE-PROVIDER", fallbackFactory = ServiceProviderConsumerFeignServiceFallbackFactory.class)
public interface ServiceProviderConsumerFeignService {
	
	/**
	 * 获取请求URL，对应着服务提供者中的方法
	 * @return
	 */
	@RequestMapping("/provider/request/info")
	public String requestInfo() ;
}

```
#### fallbackFactory代码
```
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

/**
 * 该类实现FallbackFactory这个接口，并注入需要被hystrix监控的接口ServiceProviderConsumerFeignService，
 * 用来处理被hystrix监控的服务发生异常后的处理过程
 * @author shanglishuai
 *
 */
@Component
public class ServiceProviderConsumerFeignServiceFallbackFactory implements FallbackFactory<ServiceProviderConsumerFeignService> {

	@Override
	public ServiceProviderConsumerFeignService create(Throwable cause) {
		return new ServiceProviderConsumerFeignService() {
			
			@Override
			public String requestInfo() {
				try {
					/*
					 * 休眠2s，模拟被hystrix监控的服务发生异常后的处理过程
					 */
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				return "【/provider/request/info】服务异常。。。。。";
			}

		};
	}
	
}
```

### 测试
启动本项目后，通过浏览器内访问`http://localhost:9000/consumer/request/info`，在服务正常的情况下会返回请求的URL, 在服务不正常的情况将会返回`【/provider/request/info】服务异常。。。。。`这样的消息。为了测试是否达到了熔断机制，直接启动本项目即可，不需要启动`Eureka Server`注册中心和服务提供者工程。


博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[服务消费端开启hystrix熔断机制（基于Feign）](https://blog.csdn.net/myNameIssls/article/details/81626878) <br>

参考链接：<br >
[http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__circuit_breaker_hystrix_clients.html](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__circuit_breaker_hystrix_clients.html) <br >
[http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi_spring-cloud-feign.html#spring-cloud-feign-hystrix](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi_spring-cloud-feign.html#spring-cloud-feign-hystrix)











