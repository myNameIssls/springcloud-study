package cn.tyrone.springcloud.config.client;

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
