# springcloud-service-provider-high-available

## springcloud-service-provider-high-available 工程概述
这个工程是微服务架构中服务提供者高可用项目示例，该项目完成两个节点的配置，其中一个节点端口号是8000，另一个节点的端口号是8001，同时将高可用节点注册到`Eureka Server`高可用服务注册中心。

## 实现步骤分析
`springcloud-service-provider-high-available`服务提供者高可用项目与`springcloud-service-provider`服务提供者单节点项目的程序、依赖都是一致的，不一致的地方是将单节点的服务配置更换成高可用的服务配置，需要更改配置文件。
所以，在搭建`springcloud-service-provider-high-available`服务提供者高可用项目时，优先掌握`springcloud-service-provider`服务提供者单节点项目的配置及启动。
`springcloud-service-provider`服务提供者单节点项目博客地址：[https://blog.csdn.net/mynameissls/article/details/81193557](https://blog.csdn.net/mynameissls/article/details/81193557)
`springcloud-service-provider`服务提供者单节点项目`GitHub`地址：[https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-service-provider](https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-service-provider)

### 服务提供者高可用配置
```
# 配置springcloud-service-provider-high-available公共部分
spring:
  application:
    # 用于指定Eureka Server中application的名称
    name: microservice-service-provider

eureka:
  client:
    service-url: 
      defaultZone: http://peer1:8761/eureka/,http://peer2:8762/eureka/
  instance:
    prefer-ip-address: true
   
info: 
  app:
    name: microservice-service-provider
    GitHub: https://github.com/myNameIssls/springcloud-study
    Blog: https://blog.csdn.net/column/details/24459.html
    
---
spring:
  profiles: peer1
server:
  port: 8000 # 第一个节点端口

eureka:
  instance:
    instance-id: microservice-service-provider-8000

---
spring:
  profiles: peer2
server:
  port: 8001 # 第二个节点端口
eureka:
  instance:
    instance-id: microservice-service-provider-8001
      
```

### 启动项目
启动节点一：`java -jar springcloud-service-provider-high-available-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1`
启动节点二：`java -jar springcloud-service-provider-high-available-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2`

注意事项：
启动本示例之前，需要提前启动`Eureka Server`高可用工程，即`springcloud-eureka-server-peer`。


博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[基于SpringCloud搭建服务提供者高可用示例](https://blog.csdn.net/myNameIssls/article/details/81349149)











