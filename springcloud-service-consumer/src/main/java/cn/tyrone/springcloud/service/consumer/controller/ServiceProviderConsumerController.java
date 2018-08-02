package cn.tyrone.springcloud.service.consumer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController // @RestController <==> @Controller + @ResponseBody
public class ServiceProviderConsumerController {

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * 这个前缀是服务提供者（springcloud-service-provider）注册到EurkaServer中的服务名称
	 */
	private static final String REST_URL_PREFIX = "http://MICROSERVICE-SERVICE-PROVIDER";

	@RequestMapping("/consumer/service1")
	public String service1() {
		/*
		 * 通过restful实现对服务提供者所提供方法的调用
		 */
		String url = REST_URL_PREFIX + "/provider/service1";
		return restTemplate.postForObject(url, null, String.class);
	}

	@RequestMapping("/consumer/service/discovery")
	public Object discovery() {
		String url = REST_URL_PREFIX + "/service/discovery";
		return restTemplate.postForObject(url, null, Object.class);
	}

	// 获取请求URL
	@RequestMapping("/consumer/request/info")
	public String requestInfo(HttpServletRequest request) {
		String url = REST_URL_PREFIX + "/provider/request/info";
		return restTemplate.postForObject(url, null, String.class);
	}

}
