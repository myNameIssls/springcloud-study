# springcloud-config-client

## springcloud-config-client 工程概述
该工程是配置客户端工程，用于消费由配置服务端提供的配置

## 实现步骤分析
### 引入相关依赖依赖

```
<dependencies>
	<!-- SpringCloud Config客户端 -->
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-config</artifactId>
	</dependency>
</dependencies>
```
### 创建bootstrap.yml文件
```
spring:
  cloud:
    config:
      name: application   # git服务器上配置文件名称，无后缀
      profile: dev
      label: master
      uri: http://localhost:7100   # config server端地址
```
注意: `bootstrap.yml`文件加载优先级是最高的，这个文件中的配置将会覆盖`application.yml`中的配置，所以用`bootstrap.yml`来加载服务端配置，`application.yml`这个文件可以用来配置私有的配置。


### 创建springcloud-config-client启动类
```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired Environment env; // 用于读取配置文件信息
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String server_port = env.getProperty("server.port");
		String spring_application_name = env.getProperty("spring.application.name");
		
		log.info("----------------- 读取配置文件信息如下 -----------------");
		log.info("----------------- server.port:" + server_port + " -----------------");
		log.info("----------------- spring.application.name:" + spring_application_name + " -----------------");
		
	}

}
```


### 测试
优先启动配置服务端工程，即`springcloud-config-server`, 其次启动`springcloud-config-client`这个工程。
观察控制日志输出：
```
2018-08-16 16:05:14.288  INFO 2267 --- [           main] c.t.s.config.client.Application          : ----------------- 读取配置文件信息如下 -----------------
2018-08-16 16:05:14.288  INFO 2267 --- [           main] c.t.s.config.client.Application          : ----------------- server.port:7100 -----------------
2018-08-16 16:05:14.288  INFO 2267 --- [           main] c.t.s.config.client.Application          : ----------------- spring.application.name:springcloud-config-server -----------------
```


















博客地址： </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061) </br>
[使用zuul实现服务路由请求分发](https://blog.csdn.net/myNameIssls/article/details/81675242) <br>

参考链接：<br >
[http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__router_and_filter_zuul.html](http://cloud.spring.io/spring-cloud-static/Finchley.SR1/multi/multi__router_and_filter_zuul.html) <br >
[https://blog.csdn.net/wqh8522/article/details/79094412](https://blog.csdn.net/wqh8522/article/details/79094412) <br >
[https://www.cnblogs.com/520playboy/p/7234218.html](https://www.cnblogs.com/520playboy/p/7234218.html)











