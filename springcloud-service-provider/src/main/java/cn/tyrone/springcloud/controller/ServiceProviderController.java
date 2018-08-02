package cn.tyrone.springcloud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个类用于说明
 * 
 * @author shanglishuai
 *
 */
@RestController // @RestController <==> @Controller + @ResponseBody
public class ServiceProviderController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/provider/service1")
	public String service1() {

		String str = "这是服务生产者提供的一个服务";

		return str;
	}

	// 查看服务相关信息
	@RequestMapping("/service/discovery")
	public Object discovery() {

		List<ServiceInstance> instances = discoveryClient.getInstances("MICROSERVICE-SERVICE-PROVIDER");

		// for (ServiceInstance serviceInstance : instances) {
		// System.out.println(serviceInstance.getHost());
		// }
		List<String> services = discoveryClient.getServices();

		return instances;
	}

	// 获取请求URL
	@RequestMapping("/provider/request/info")
	public String requestInfo(HttpServletRequest request) {
		return request.getRequestURL().toString();
	}

}
