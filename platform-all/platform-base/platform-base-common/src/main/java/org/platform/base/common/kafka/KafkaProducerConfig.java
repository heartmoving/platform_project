package org.platform.base.common.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.platform.base.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * kafka 配置数据
 * @author zhang kui
 */
@Configuration
@PropertySource(value={"classpath:application.properties"},ignoreResourceNotFound=true)
public class KafkaProducerConfig {

	/**注入map数据**/
	@Autowired
    private Environment map;
	
	/**
	 * 初始化kafka
	 * @return
	 */
	@Bean(name="kafkaProducerBean")
	public KafkaTemplate<String, String> kafkaTemplate() {
		String isOpen = map.getProperty("kafka.isopen");
		/**不开启不初始化**/
		if(StringUtils.isBlank(isOpen) || "false".equals(isOpen)){
			return null;
		}
		return new KafkaTemplate<String, String>(producerFactory());
	}

	
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	/**
	 * 初始化kafka配置信息数据
	 * @return
	 */
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, map.getProperty("kafka.producer.servers"));
		props.put(ProducerConfig.RETRIES_CONFIG, Integer.parseInt(map.getProperty("kafka.producer.retries")));
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.parseInt(map.getProperty("kafka.producer.batch.size")));
		props.put(ProducerConfig.LINGER_MS_CONFIG, Integer.parseInt(map.getProperty("kafka.producer.linger")));
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, Integer.parseInt(map.getProperty("kafka.producer.buffer.memory")));
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
		return props;
	}
}
