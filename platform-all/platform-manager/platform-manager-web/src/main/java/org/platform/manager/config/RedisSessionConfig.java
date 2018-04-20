package org.platform.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.DefaultCookieSerializer;

import redis.clients.jedis.JedisPoolConfig;
/**
 * session 跨域处理配置
 * @author zhang kui
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=30*60)
public class RedisSessionConfig {
 
	   @Value("${session.redis.host}")
	   private String hostName;
	   @Value("${session.redis.password}")
	   private String password;
	   @Value("${session.redis.port}")
	   private Integer port;
	   @Value("${session.redis.maxTotal}")
	   private Integer maxTotal;
	   @Value("${session.redis.maxIdle}")
	   private Integer maxIdle;
	   @Value("${session.redis.minIdle}")
	   private Integer minIdle;
	   @Value("${session.redis.timeout}")
	   private Integer timeout;
	   @Value("${session.cookieName}")
	   private String cookieName;
	   @Value("${session.cookiePath}")
	   private String cookiePath;
	   @Value("${session.domainName}")
	   private String domainName;
	   
	   
	   /**
	    * 设置redis 连接
	    * @return
	    */
	   @Bean
       public JedisConnectionFactory initJedisConnectionFactory(){
		   JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		   jedisPoolConfig.setMaxIdle(maxIdle);
		   jedisPoolConfig.setMaxTotal(maxTotal);
		   jedisPoolConfig.setMinIdle(minIdle);
		   JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig);
		   connectionFactory.setHostName(hostName);
		   connectionFactory.setPassword(password);
		   connectionFactory.setPort(port);
		   connectionFactory.setTimeout(timeout);
		   connectionFactory.setUsePool(true);
		   return connectionFactory;
       }
	   
	   /**
	    * 设置cookie 跨域处理
	    * @return
	    */
	   @Bean
	   public CookieHttpSessionStrategy initCookieHttpSessionStrategy(){
		   DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		   cookieSerializer.setCookieName(cookieName);
		   cookieSerializer.setDomainName(domainName);
		   CookieHttpSessionStrategy cookieHttpSessionStrategy = new CookieHttpSessionStrategy();
		   cookieHttpSessionStrategy.setCookieSerializer(cookieSerializer);
		   return cookieHttpSessionStrategy;
	   }
	   
}
