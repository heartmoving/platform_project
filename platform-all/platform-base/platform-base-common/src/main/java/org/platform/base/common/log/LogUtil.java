package org.platform.base.common.log;

import org.apache.log4j.Logger;
import org.platform.base.common.application.ApplicationContextUtil;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 自定义log4j的日志信息
 * @author zhang kui
 */
public class LogUtil {
	
	
	
	/**
	 * 是否需要kafka记录日志信息，需要则打印到kafka中
	 * @param LOG
	 * @param e
	 */
	@SuppressWarnings({"unchecked" })
	public static void error(Logger LOG,String msg,Exception e){
		KafkaTemplate<String, String> kafkaTemplate = (KafkaTemplate<String, String>)(ApplicationContextUtil.getApplicationContext().getBean("kafkaProducerBean"));
		/**推送到kafka服务器处理信息**/
		if(null != kafkaTemplate){
			
		}else{
			LOG.error(msg,e);
		}
	}
}
