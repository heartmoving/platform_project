package org.platform.base.common.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
/**
 * 查询List<Map> 转Object
 * @author zhang kui
 */
public class ListMapConvertObjectUtils {

	/**
	 * 将查询出来的List Map 转化成 List<Object>
	 * @param clazz
	 * @param resultMap
	 * @return
	 */
	public static <T> List<T>  getListObject(Class<T> clazz,List<Map<String,Object>> resultMap){
		List<T> listT = new ArrayList<T>();
		for(Map<String,Object> map : resultMap){
			try {
				T  t = clazz.newInstance();
				Set<Entry<String, Object>>  entries = map.entrySet();
				Iterator<Entry<String, Object>> iterator = entries.iterator();
				while(iterator.hasNext()){
					Entry<String, Object> entry = iterator.next();
					String key = entry.getKey();
					Object value = entry.getValue();
					if(null != key && !("".equals(key))){
						key = key.toLowerCase();
						String methodname = initcapSet(key);
						String attr = getAttribute(key);
						/**排除父类公共日期字段createdStamp,createdTxStamp,lastUpdatedStamp,lastUpdatedTxStamp**/
						if("createdStamp".equals(attr) || "createdTxStamp".equals(attr)
								|| "lastUpdatedStamp".equals(attr) || "lastUpdatedTxStamp".equals(attr)){
							Method method = t.getClass().getMethod(methodname, Timestamp.class);
							method.invoke(t, value);
						}else{
							Class<?> clazztype = t.getClass().getDeclaredField(attr).getType();
							Method method = t.getClass().getMethod(methodname, clazztype);
							method.invoke(t, typeConvert(clazztype.getName(),value));
						}
					}
				}
				listT.add(t);
			 }catch (Exception e) {
				 e.printStackTrace();
			 }
		}
		return listT;
	}
	
	/**
	 * map 转对象
	 * @param clazz
	 * @param resultMap
	 * @return
	 */
	public static <T> T  getObject(Class<T> clazz,Map<String,Object> resultMap){
		T  t = null;
		try {
			t = clazz.newInstance();
			Set<Entry<String, Object>>  entries = resultMap.entrySet();
			Iterator<Entry<String, Object>> iterator = entries.iterator();
			while(iterator.hasNext()){
				Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				if(null != key && !("".equals(key))){
					key = key.toLowerCase();
					String methodname = initcapSet(key);
					String attr = getAttribute(key);
					/**排除父类公共日期字段createdStamp,createdTxStamp,lastUpdatedStamp,lastUpdatedTxStamp**/
					Class<?> clazztype = t.getClass().getDeclaredField(attr).getType();
					Method method = t.getClass().getMethod(methodname, clazztype);
					method.invoke(t, typeConvert(clazztype.getName(),value));
				}
			}
		 }catch (Exception e) {
		 }
		return t;
	}
	/**
	 * 功能：将输入字符串的首字母改成大写
	 * @param str 表字段
	 * @return
	 */
	public static String initcapSet(String str) {
		StringBuilder sb = new StringBuilder("set");
		if(str.indexOf("_") > 0){
			String strArr[] = str.split("_");
			for (String string : strArr) {
				char[] ch = string.toCharArray();
				if(ch[0] >= 'a' && ch[0] <= 'z'){
					ch[0] = (char)(ch[0] - 32);
				}
				sb.append(new String(ch));
			}
		}else{
			char[] ch = str.toCharArray();
			if(ch[0] >= 'a' && ch[0] <= 'z'){
				ch[0] = (char)(ch[0] - 32);
			}
			sb.append(new String(ch));
		}
		return sb.toString();
	}
	
	
	/**
	 * 获取实体属性名字
	 * @return
	 */
	public static String getAttribute(String str){
		StringBuilder sb = new StringBuilder();
		if(str.indexOf("_") > 0){
			String strArr[] = str.split("_");
			int i = 0;
			for (String string : strArr) {
				if(i>0){
					char[] ch = string.toCharArray();
					if(ch[0] >= 'a' && ch[0] <= 'z'){
						ch[0] = (char)(ch[0] - 32);
					}
					sb.append(new String(ch));
				}else{
					sb.append(string);
				}
				i++;
			}
		}else{
			sb.append(str);
		}
		return sb.toString();
	}
	
	/**
	 * 转成值
	 * @param typestr
	 * @param ob
	 * @return
	 * @throws Exception
	 */
	private static Object typeConvert(String typestr,Object ob) throws Exception{
		if(null != ob && !("".equals(ob))){
			if("java.lang.Integer".equals(typestr)){
				Integer value = Integer.parseInt(ob+"");
				return value;
			}else if("java.lang.Double".equals(typestr)){
				Double value = Double.parseDouble(ob+"");
				return value;
			}else if("java.lang.Float".equals(typestr)){
				Float value = Float.parseFloat(ob+"");
				return value;
			}else if("java.lang.String".equals(typestr)){
				return ob+"";
			}else if("java.lang.Long".equals(typestr)){
				Long value = Long.parseLong(ob+"");
				return value;
			}else if("java.lang.Short".equals(typestr)){
				Short value = Short.parseShort(ob+"");
				return value;
			}else if("java.lang.Byte".equals(typestr)){
				Byte value = Byte.parseByte(ob+"");
				return value;
			}else if("java.math.BigDecimal".equals(typestr)){
				BigDecimal bigDecimal = new BigDecimal(ob+"");
				return bigDecimal;
			}else if("java.util.Date".equals(typestr)){
				return DateUtils.fomatDate(ob+"");
			}
		}
		return null;
	}
	
	
}
