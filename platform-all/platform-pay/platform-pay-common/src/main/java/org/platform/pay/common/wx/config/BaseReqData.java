package org.platform.pay.common.wx.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * 
 * @author yuliang
 *
 */
public class BaseReqData {
	
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<xml>");
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object obj;
			try {
				obj = field.get(this);
				if (obj != null) {
					if (obj instanceof String) {
						String str = (String) obj;
						if (!"".equals(str)) {
							map.put(field.getName(), obj);
							sb.append("<"+field.getName()+"><![CDATA["+obj+"]]></"+field.getName()+">");
						}
					} else {
						map.put(field.getName(), obj);
						sb.append("<"+field.getName()+"><![CDATA["+obj+"]]></"+field.getName()+">");
					}
					// map.put(field.getName(), obj);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object obj;
			try {
				obj = field.get(this);
				if (obj != null) {
					if (obj instanceof String) {
						String str = (String) obj;
						if (!"".equals(str)) {
							map.put(field.getName(), obj);
						}
					} else {
						map.put(field.getName(), obj);
					}
					// map.put(field.getName(), obj);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		System.out.println(map);
		return map;
	}
	
	/**
	 * 初始化xml
	 * @param map
	 * @return
	 */
	public static  String toXml(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<xml>");
		Set<Entry<String, String>> entries =  map.entrySet();
		Iterator<Entry<String, String>> iterator = entries.iterator();
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			sb.append("<"+entry.getKey()+"><![CDATA["+entry.getValue()+"]]></"+entry.getKey()+">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

}
