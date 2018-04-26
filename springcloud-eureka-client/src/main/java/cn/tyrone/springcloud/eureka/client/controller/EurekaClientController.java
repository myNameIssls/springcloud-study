package cn.tyrone.springcloud.eureka.client.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaClientController {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired private DiscoveryClient discoveryClient;
	
	@RequestMapping("/index")
	public String index() {
		
		List<String> services = discoveryClient.getServices();
		List<ServiceInstance> instances = discoveryClient.getInstances("");
		logger.info("services:" + services);
		logger.info("instances:" + instances);
		return services.toString() + "\n" + instances.toString();
	}
	
}
