# springcloud-zuul

## springcloud-zuul 工程概述
该工程通过整合zuul实现了服务路由设置，请求分发的功能。

## 实现步骤分析
### 引入相关依赖依赖

```
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-zuul</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
</dependencies>
```
### 创建application.yml文件
```
spring:
  application:
    name: microservice-service-zuul

server:
  port: 7000

eureka:
  client:
    service-url: 
      # eureka Server端地址
      defaultZone: http://peer1:8761/eureka/,http://peer2:8762/eureka/
 
zuul:
  # 忽略单个服务实例：
  # ignored-services: microservice-service-provider
  # "*"表示忽略所有微服务，所有请求均不能通过服务的应用名称来访问
  # 例如：http://localhost:7000/tyrone/microservice-service-provider/provider/request/info是访问不通的
  # 如果不声明此属性，上述请求是可以访问成功的
  ignored-services: "*"
  # 指定路由前缀,所有请求均需要带此前缀
  prefix: /tyrone
  routes:
    provider: # 相当于服务路由模块名称，如果与service-id相同，那么service-id可以不用声明
      service-id: microservice-service-provider # 表示微服务的应用名称，需要与Eureka Server中保持一致
      path: /api/** # 以/api开始的请求均可以映射到service-id代表的微服务应用上
```

### 创建springcloud-zuul启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy	// 开启zuul代理
@SpringBootApplication
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
```

### 测试
本项目的测试需要启动`springcloud-eureka-server-peer`和`springcloud-service-provider-high-available`这个工程，其次启动本项目。
本项目启动成功通过访问：[http://localhost:7000/tyrone/api/provider/request/info](http://localhost:7000/tyrone/api/provider/request/info)可查看结果。如果返回结果和服务提供者一致，则说明路由生效。

博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[服务消费端开启hystrix熔断机制（基于Feign）](https://blog.csdn.net/myNameIssls/article/details/81626878) <br>

参考链接：<br >
[http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__circuit_breaker_hystrix_clients.html](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__circuit_breaker_hystrix_clients.html) <br >
[http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi_spring-cloud-feign.html#spring-cloud-feign-hystrix](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi_spring-cloud-feign.html#spring-cloud-feign-hystrix)











