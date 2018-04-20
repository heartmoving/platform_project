package org.platform.base.common.util;


/**
 * @Title: ReflectionTypeUtils.java
 * @Package org.platform.base.mybaties
 * @Description: TODO 反射简单类型映射处理
 * @author zhang kui
 * @date 2014-12-19 下午12:53:42
 * @version V1.0
 */
public class ReflectionTypeUtils {
	
	
	/**
	 * 转换对象
	 * @param value 格式化值
	 * @param type 类型
	 * @return
	 */
	public static Object convertObject(String value,String type){
		/**boolean 处理**/
		if("boolean".equals(type) || "java.lang.Boolean".equals(type)){
			
			return Boolean.parseBoolean(value);
		}else if("int".equals(type) || "java.lang.Integer".equals(type)){
			
			return Integer.parseInt(value);
		}else if("double".equals(type) || "java.lang.Double".equals(type)){
			
			return Double.parseDouble(value);
		}else if("string".equals(type) || "java.lang.String".equals(type)){
			
			return value;
		}else if("long".equals(type) || "java.lang.Long".equals(type)){
			
			return Long.parseLong(value);
		}else if("short".equals(type) || "java.lang.Short".equals(type)){
			
			return Short.parseShort(value);
		}else if("byte".equals(type) || "java.lang.byte".equals(type)){
			
			return Byte.parseByte(value);
		}else if("float".equals(type) || "java.lang.Float".equals(type)){
			
			return Float.parseFloat(value);
		}
		return value;
	}
	
	public static void main(String[] args) {
		System.out.println(Long.class.getName());
	}
}
