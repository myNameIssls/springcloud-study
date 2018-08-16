# springcloud-config-server

## springcloud-config-server 工程概述
该工程是配置服务端工程，用于统一管理各个工程中的配置，进而达到配置文件与应用程序的解耦

## 实现步骤分析
### 引入相关依赖依赖

```
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-server</artifactId>
	</dependency>
</dependencies>
```
### 创建application.yml文件
```
server:
  port: 7100
spring:
  application:
    name: springcloud-config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/myNameIssls/springcloud-study # git服务器上配置文件地址
```
注意：这里需要在相应的git服务器创建配置文件仓库，配置文件需要放置配置文件仓库的根目录

### 创建springcloud-config-server启动类
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
```

### 配置文件访问规则
```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

### 测试
启动本项目在浏览器中访问:
```
http://localhost:7100/application/dev
http://localhost:7100/application/dev/master
http://localhost:7100/application-dev.yml
http://localhost:7100/master/application-dev.yml
```
结果便是git服务器上`application-dev.yml`的内容。

博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[使用zuul实现服务路由请求分发](https://blog.csdn.net/myNameIssls/article/details/81675242) <br>

参考链接：<br >
[http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__router_and_filter_zuul.html](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__router_and_filter_zuul.html) <br >
[https://blog.csdn.net/wqh8522/article/details/79094412](https://blog.csdn.net/wqh8522/article/details/79094412) <br >
[https://www.cnblogs.com/520playboy/p/7234218.html](https://www.cnblogs.com/520playboy/p/7234218.html)











