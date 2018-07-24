# springcloud-service-provider

## springcloud-service-provider 工程概述
这个工程是是一个微服务工程，用来向`Eureka Server`中注册一个服务，相当于服务提供者。

## 实现步骤分析
### 引入Eureka Server Client依赖

```
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
	</dependency>
</dependencies>
```
### 创建application.yml文件
```
server:
  port: 8000
  
spring:
  application:
    # 用于指定Eureka Server中application的名称
    name: microservice-service-provider
eureka:
  client:
    service-url:
      # defaultZone: http://localhost:8761/eureka/
      # Eureka Server高可用配置，注意多个节点采用英文逗号连接，且不允许出现其它的特殊字符（例如：空格符）
      defaultZone: http://peer1:8761/eureka/,http://peer2:8762/eureka/
  instance:
    # 用于指定Eureka Server中的服务状态名称
    instance-id: microservice-service-provider-8000
    # 用于标记选中Eureka Server中的服务状态名称时，
    # 可以从浏览器左下角看到IP地址
    # 默认是false
    prefer-ip-address: true

# info 模块用来显示app相关信息，如果不配置info模块，那么点击Eureka Server中的状态链接时，会出现访问异常
# 使用info模块需要在pom.xml中添加spring-boot-starter-actuator依赖    
info: 
  app:
    name: microservice-service-provider
    GitHub: https://github.com/myNameIssls/springcloud-study
    Blog: https://blog.csdn.net/column/details/24459.html
      
```
注意：
`spring-boot-starter-actuator`的`GAV`地址：

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
`spring-boot-starter-actuator`属于`SpringBoot`模块中的依赖，所以也需要引入`SpringBoot`依赖，`GAV`如下：

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-dependencies</artifactId>
	<version>1.5.9.RELEASE</version>
	<type>pom</type>
	<scope>import</scope>
</dependency>
```



### 创建springcloud-service-provider启动类
```
@SpringBootApplication
@EnableEurekaClient  // 服务发现
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
```
### 创建服务示例类
```
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个类用于说明
 * @author shanglishuai
 *
 */
@RestController // @RestController <==> @Controller + @ResponseBody
public class ServiceProviderController {
	
	@Autowired private DiscoveryClient discoveryClient;
	
	@RequestMapping("/provider/service1")
	public String service1() {
		
		String str = "这是服务生产者提供的一个服务";
		
		return str;
	}
	
	// 查看服务相关信息
	@RequestMapping("/service/discovery")
	public Object discovery() {
		
		List<ServiceInstance> instances = discoveryClient.getInstances("MICROSERVICE-SERVICE-PROVIDER");
		
//		for (ServiceInstance serviceInstance : instances) {
//			System.out.println(serviceInstance.getHost());
//		}
		List<String> services = discoveryClient.getServices();
		
		return instances;
	}
	
	
}

```

博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[基于Eureka Server实现服务注册高可用](https://blog.csdn.net/myNameIssls/article/details/81157345)

参考链接:  </br>
[http://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#spring-cloud-eureka-server-peer-awareness](http://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#spring-cloud-eureka-server-peer-awareness)










