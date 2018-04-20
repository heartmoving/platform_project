package org.platform.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@ComponentScan({"org.platform.*"})
public class ManagerApplication  extends SpringBootServletInitializer{

	
	
	public static void main(String[] args) {
		
		SpringApplication.run(ManagerApplication.class, args);
	}
}
