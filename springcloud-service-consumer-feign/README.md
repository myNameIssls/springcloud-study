# springcloud-service-consumer-feign

## springcloud-service-consumer-feign 工程概述
这个工程是是一个微服务消费者工程，负责消费服务提供者（springcloud-service-provider-high-available）提供的服务，该工程提供的服务与服务提供者提供的服务一一对应。
同时，该工程基于`Feign`实现了客户端负载均衡（即`Ribbon`负载均衡）。

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
  port: 9000  # 指定工程端口号   
```

### 创建springcloud-service-consumer-feign启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients	// 启动Feign
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
```
### 服务消费示例代码
#### FeignClient接口代码
```
/**
 * @FeignClient("MICROSERVICE-SERVICE-PROVIDER")
 * MICROSERVICE-SERVICE-PROVIDER:是一个服务提供者的实例名称
 * 用于标记一个可用的服务的名称，同时创建一个Ribbon负载均衡器
 *
 * 该接口用于实现与服务提供者进行交互，接口中的方法与服务提供者需要暴露的方法一一对应
 */
@FeignClient("MICROSERVICE-SERVICE-PROVIDER")
public interface ServiceProviderConsumerFeignService {
	
	@RequestMapping("/provider/service1")
	public String service1() ;

	/**
	 * 获取请求URL，对应着服务提供者中的方法
	 * @return
	 */
	@RequestMapping("/provider/request/info")
	public String requestInfo() ;
}

```

#### 消费端代码

```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tyrone.springcloud.service.consumer.feign.service.ServiceProviderConsumerFeignService;

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

	@RequestMapping("/consumer/service1")
	public String service1() {
		return this.feignService.service1();
	}

	/**
	 * 服务消费端方法
	 * 该方法通过与@FeignClient("MICROSERVICE-SERVICE-PROVIDER")标记的接口实现与服务提供者的交互
	 * @return
	 */
	@RequestMapping("/consumer/request/info")
	public String requestInfo() {
		return feignService.requestInfo();
	}

}

```

### 测试
启动本项目后，通过浏览器内反复访问`http://localhost:9000/consumer/request/info`，该请求返回的是`http`请求相关的信息，对应着服务端的相应方法。

注意事项：
- 启动本示例之前，需要提前启动`Eureka Server`高可用工程（`springcloud-eureka-server-peer`）和服务提供者高可用工程（`springcloud-service-provider-high-available`）



博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[基于Feign实现服务消费](https://blog.csdn.net/myNameIssls/article/details/81510474) <br>

参考链接：
[http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi_spring-cloud-feign.html](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi_spring-cloud-feign.html)











