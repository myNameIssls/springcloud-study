# springcloud-service-consumer

## springcloud-service-consumer 工程概述
这个工程是是一个微服务消费者工程，负责消费服务提供者（springcloud-service-provider）提供的服务

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
      defaultZone: http://peer1:8761/eureka/ 
      
```

### 创建springcloud-service-consumer启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
```
### 服务消费示例代码
```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController // @RestController <==> @Controller + @ResponseBody
public class ServiceProviderConsumerController {
	
	@Autowired private RestTemplate restTemplate;
	
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
}

```



注意事项：
启动本示例之前，需要提前启动`Eureka Server`高可用工程，即`springcloud-eureka-server-peer`。


博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[基于SpringCloud完成服务提供者示例](https://blog.csdn.net/myNameIssls/article/details/81193557)











