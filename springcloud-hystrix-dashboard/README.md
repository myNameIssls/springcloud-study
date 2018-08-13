# springcloud-hystrix-dashboard

## springcloud-hystrix-dashboard 工程概述
该工程引入`Hystrix Dashboard`实现对微服务的实时监控及界面化监控。

## 实现步骤分析
### 引入相关依赖依赖

```
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
	</dependency>
</dependencies>
```
### 创建application.yml文件
```
spring:
  application:
    name: hystrix-dashboard
server:
  port: 9100
```

### 创建springcloud-hystrix-dashboard启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard // 启动hystrix监控面板
@SpringBootApplication
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
```
此时，启动项目，并在浏览器里访问`http://localhost:9100/hystrix/`即可进入`Hystrix Dashboard`监控首页面。
监控首页面根据不同的架构模式提供了有一种监控方式：

1. Cluster via Turbine (default cluster): http://turbine-hostname:port/turbine.stream（这是针对Turbine集群（默认集群））
2. Cluster via Turbine (custom cluster): http://turbine-hostname:port/turbine.stream?cluster=[clusterName](针对自定义集群模式)
3. Single Hystrix App: http://hystrix-app:port/hystrix.stream（单体应用模式）

以上仅实现了`Hystrix Dashboard`监控系统搭建，下面介绍如何实现对服务的监控

## `Hystrix Dashboard`服务监控

### Single Hystrix App单体应用模式
要实现服务的监控，需要修改被监控服务的配置及依赖，本例以[springcloud-service-consumer-feign-hystrix](https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-service-consumer-feign-hystrix)这个工程为服务消费端介绍如何实现监控。

#### 修改消费端pom依赖，引入`Hystrix Dashboard`依赖
需要增加以下依赖：
```
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
#### 启动熔断机制
在主启动类上增加`@EnableCircuitBreaker` 
修改后的启动类如下：
```
@SpringBootApplication
@EnableFeignClients // 启动Feign
@EnableCircuitBreaker // 启动熔断机制
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
```

### 测试
分别启动`springcloud-hystrix-dashboard`和`springcloud-service-consumer-feign-hystrix`这两个工程，在浏览器里多次访问`http://localhost:9000/consumer/request/info`，并通过`http://localhost:9100/hystrix/monitor?stream=http://localhost:9000/hystrix.stream`即可查看监控的详细信息


## Cluster via Turbine (default cluster) | Cluster via Turbine (custom cluster)
后续补齐




博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[Hystrix Dashboard监控面板](https://blog.csdn.net/myNameIssls/article/details/81633358) <br>

参考链接：<br >
[http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__circuit_breaker_hystrix_dashboard.html](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__circuit_breaker_hystrix_dashboard.html) <br >
[http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__hystrix_timeouts_and_ribbon_clients.html](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__hystrix_timeouts_and_ribbon_clients.html) <br >
[https://blog.csdn.net/yejingtao703/article/details/77683102](https://blog.csdn.net/yejingtao703/article/details/77683102) <br >
[https://www.cnblogs.com/chenweida/p/9025589.html](https://www.cnblogs.com/chenweida/p/9025589.html)













