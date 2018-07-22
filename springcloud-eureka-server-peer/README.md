# springcloud-eureka-server-peer

## springcloud-eureka-server-peer 概述
springcloud-eureka-server-peer这个工程是基于`Eureka Server`搭建的集群环境。

## 实现步骤分析
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
spring:
  application:
    name: eureka-server-peer
eureka:
  client:
    register-with-eureka: false # 表示不向注册中心注册
    fetch-registry: false # 由于注册中心的职责就是维护服务实例，所以它不需要去检索服务

---
spring:
  profiles: peer1
server:
  port: 8761
eureka:
  instance:
    hostname: peer1
  client:
    service-url:
      defaultZone: http://peer2:8762/eureka/

---
spring:
  profiles: peer2
server:
  port: 8762
eureka:
  instance:
    hostname: peer2
  client:
    service-url:
      defaultZone: http://peer1:8761/eureka/
      
```
注意：这个配置分为三部分，每一部分通过`---`来分割。
第一部分配置了`Eureka Server`高可用配置的公共部分，例如：应用名称、禁止把自身当做服务来注册
第二部分是`peer1`节点的配置，第三部分是`peer2`节点的配置。
由于当前是在一台主机上模拟`Eureka Server`高可用，所以`peer1`和`peer2`两个主机需要提前在`hosts`文件中注册一下。
注册方式：使用`vim`编辑本机`hosts`文件($ sudo vim /etc/hosts)，并在其中添加`127.0.0.1 peer1 peer2`主机映射

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

### 启动Eureka Server
启动`peer1`节点：`java -jar springcloud-eureka-server-peer-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1`
启动`peer2`节点：`java -jar springcloud-eureka-server-peer-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2`

博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[基于Eureka Server实现服务注册高可用](https://blog.csdn.net/myNameIssls/article/details/81157345)

参考链接:  </br>
[http://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#spring-cloud-eureka-server-peer-awareness](http://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#spring-cloud-eureka-server-peer-awareness)










