package cn.tyrone.springcloud.service.consumer.feign.hystrix.service;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

/**
 * 该类实现FallbackFactory这个接口，并注入需要被hystrix监控的接口ServiceProviderConsumerFeignService，
 * 用来处理被hystrix监控的服务发生异常后的处理过程
 * @author shanglishuai
 *
 */
@Component
public class ServiceProviderConsumerFeignServiceFallbackFactory implements FallbackFactory<ServiceProviderConsumerFeignService> {

	@Override
	public ServiceProviderConsumerFeignService create(Throwable cause) {
		return new ServiceProviderConsumerFeignService() {
			
			@Override
			public String requestInfo() {
				try {
					/*
					 * 休眠2s，模拟被hystrix监控的服务发生异常后的处理过程
					 */
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				return "【/provider/request/info】服务异常。。。。。";
			}

		};
	}
	
}
