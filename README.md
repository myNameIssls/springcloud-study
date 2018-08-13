# springcloud-study
## springcloud-study 概述
这个工程主要是为了学习SpringCloud搭建的基础工程（父工程），在之后的基础教程案例中都会基于这个基础工程来实现。

## 项目工程搭建
### 创建maven项目
注意父工程作为整个SpringCloud学习的基础工程，所以其中不包括具体的模块实现，仅包括全局的属性设置，例如：依赖版本、插件之类。 
所以这个工程的pom文件的packaging属性值是pom。 

```
<groupId>cn.tyrone.springcloud</groupId>
<artifactId>springcloud-study</artifactId>
<version>0.0.1-SNAPSHOT</version>
<packaging>pom</packaging>

```

### 引入` SpringCloud `依赖

```
<dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Edgware.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>

</dependencyManagement>

```

## 项目模块健介绍 </br>
- [springcloud-eureka-server](https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-eureka-server) </br>
- [springcloud-eureka-server-peer](https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-eureka-server-peer) </br>
- [springcloud-service-provider](https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-service-provider) </br>
- [springcloud-service-provider-high-available](https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-service-provider-high-available) <br >
- [springcloud-service-consumer](https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-service-consumer) <br >
- [springcloud-service-consumer-feign](https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-service-consumer-feign) <br >
- [springcloud-service-consumer-feign-hystrix](https://github.com/myNameIssls/springcloud-study/tree/master/springcloud-service-consumer-feign-hystrix) <br >

## springcloud-study 博客地址 </br>
[SpringCloud 企业级应用实战](https://blog.csdn.net/mynameissls/article/details/81150061)








