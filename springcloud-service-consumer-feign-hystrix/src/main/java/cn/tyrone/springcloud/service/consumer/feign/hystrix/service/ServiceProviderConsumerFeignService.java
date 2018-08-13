package cn.tyrone.springcloud.service.consumer.feign.hystrix.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @FeignClient("MICROSERVICE-SERVICE-PROVIDER")
 * MICROSERVICE-SERVICE-PROVIDER:是一个服务提供者的实例名称
 * 用于标记一个可用的服务的名称，同时创建一个Ribbon负载均衡器
 * fallbackFactory指定用于处理服务提供者服务异常过程的类
 * 该接口用于实现与服务提供者进行交互，接口中的方法与服务提供者需要暴露的方法一一对应
 */
@FeignClient(value = "MICROSERVICE-SERVICE-PROVIDER", fallbackFactory = ServiceProviderConsumerFeignServiceFallbackFactory.class)
public interface ServiceProviderConsumerFeignService {
	
	/**
	 * 获取请求URL，对应着服务提供者中的方法
	 * @return
	 */
	@RequestMapping("/provider/request/info")
	public String requestInfo() ;
}
