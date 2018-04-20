package org.platform.zuul;

import org.platform.zuul.web.ZuulAccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulApplication  extends SpringBootServletInitializer{

	
	@Bean
	public ZuulAccessFilter accessFilter(){
		
		return new ZuulAccessFilter();
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(ZuulApplication.class, args);
	}
}
