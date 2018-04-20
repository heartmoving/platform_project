package org.platform.pay.common.wx.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.platform.base.common.util.StringUtils;


/**
 * 微信配置加载
 * @author zhang kui
 */
public class WxProperties {

	private static Logger LOG = Logger.getLogger(WxProperties.class);
	private static Map<String,String> maps = new HashMap<String, String>(); 
	static{
		try{
			Properties properties = new Properties();
			properties.load(WxProperties.class.getResourceAsStream("/wx.properties"));
			Set<Entry<Object, Object>> entries = properties.entrySet();
			Iterator<Entry<Object, Object>> iterator =  entries.iterator();
			while(iterator.hasNext()){
				Entry<Object, Object> entry = iterator.next();
				maps.put(entry.getKey().toString(), entry.getValue().toString());
			}
			/**处理替换数据**/
			String baseUrl = maps.get("baseUrl");
			Set<Entry<String, String>> entries2 = maps.entrySet();
			Iterator<Entry<String, String>> iterator2 = entries2.iterator();
			while(iterator2.hasNext()){
				Entry<String, String> entry = iterator2.next();
				String value =  entry.getValue();
				if(StringUtils.isNotBlank(value)){
					value = value.replace("${baseUrl}", baseUrl);
					maps.put(entry.getKey(), value);
				}
			}
			
		}catch(Exception e){
			LOG.error("读取配置错误，error:",e.fillInStackTrace());
		}
	}
	
	public static String getValue(String key){
	
		return maps.get(key);
	}
	
}
