# springcloud-eureka-server

## 工程概述
`springcloud-eureka-servery`这个工程是基于`Eureka Server`实现的单机环境下服务注册中心功能。

## 实现步骤
### 引入Eureka Server依赖

```
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
	</dependency>
</dependencies>
```
### 创建application.yml文件
```
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client: 
    register-with-eureka: false  # 表示不向注册中心注册
    fetch-registry: false # 由于注册中心的职责就是维护服务实例，所以它不需要去检索服务
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
      
```
### 创建Eureka Server启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```
博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[基于Eureka Server实现服务注册](https://blog.csdn.net/myNameIssls/article/details/81154755)

参考链接: </br>
[http://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#spring-cloud-eureka-server-peer-awareness](http://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#spring-cloud-eureka-server-peer-awareness)





