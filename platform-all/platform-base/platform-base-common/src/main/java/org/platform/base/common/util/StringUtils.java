package org.platform.base.common.util;



/**
 * @Title: StringUtils.java
 * @Package org.plaform.base.common.util
 * @Description: TODO 字符串验证处理
 * @author zhang kui
 * @date 2014-12-19 下午12:53:42
 * @version V1.0
 */
public class StringUtils {

	/**
	 * @author zhang kui
	 * @Title: isBlank
	 * @Description: TODO 判断字符串是否是null或者""
	 * @param @param str 字符串
	 * @param @return 是null或者""返回true,否则返回false
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isBlank(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * @author zhang kui
	 * @Title: isNotBlank
	 * @Description: TODO 判断字符串是否是null或者""
	 * @param @param str 字符串
	 * @param @return 是null或者""返回false,否则返回true
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isNotBlank(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		return true;
	}
	
}
