package org.platform.server.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册中心配置
 */
@EnableEurekaServer
@EnableEurekaClient
@SpringBootApplication
public class EurekaServer{
	
    public static void main( String[] args ){
    	SpringApplication.run(EurekaServer.class, args);
    }
}
