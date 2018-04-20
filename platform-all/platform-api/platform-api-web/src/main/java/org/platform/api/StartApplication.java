package org.platform.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("org.platform.*")
public class StartApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		
		SpringApplication.run(StartApplication.class, args);
	}
}
