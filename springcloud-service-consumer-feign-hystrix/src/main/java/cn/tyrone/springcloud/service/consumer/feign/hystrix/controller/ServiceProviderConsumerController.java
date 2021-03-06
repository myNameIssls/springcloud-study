package cn.tyrone.springcloud.service.consumer.feign.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tyrone.springcloud.service.consumer.feign.hystrix.service.ServiceProviderConsumerFeignService;

/**
 * 服务消费端
 * 这是服务消费端暴露给客户端请求的地址
 * @author shanglishuai
 *
 */
@RestController // @RestController <==> @Controller + @ResponseBody
public class ServiceProviderConsumerController {

	// 注入Feign接口
	@Autowired
	private ServiceProviderConsumerFeignService feignService;

	/**
	 * 服务消费端方法
	 * 该方法通过与@FeignClient("MICROSERVICE-SERVICE-PROVIDER")标记的接口实现与服务提供者的交互
	 * @return
	 */
	@RequestMapping("/consumer/request/info")
	public String requestInfo(boolean flag) {
		return feignService.requestInfo();
	}

}
