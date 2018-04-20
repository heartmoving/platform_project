package org.platform.base.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Title: VerificationUtils.java
 * @Package org.plaform.base.common.util
 * @Description: TODO 验证工具，此类可以写email,phone 这些类的验证
 * @author zhang kui
 * @date 2014-12-19 下午12:53:42
 * @version V1.0
 */
public class VerificationUtils {
	
	/**
	 * 验证邮箱地址是否正确
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号是否正确
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^1[3458]\\d{9}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @Title: isTelephone
	 * @Description: 验证座机号码是否正确
	 * @param @param str
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 */
	public static boolean isTelephone(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[0][1-9]{2,3}-[0-9]{7,8}$"); // 验证带区号的
		if (str.length() > 9) {
			m = p.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * @Title: stringToList
	 * @Description: 将字符串根据分隔符转化为list
	 * @param specialString
	 * @param prefix
	 *            分隔符
	 * @return List<Integer>
	 */
	public static List<String> stringToList(String specialString, String prefix) {
		List<String> list = null;
		if (isNotBlank(specialString) && isNotBlank(prefix)) {
			list = new ArrayList<String>();
			if (specialString.indexOf(prefix) > 0) {
				String[] strArr = specialString.split(prefix);
				if (null != strArr && strArr.length > 0) {
					for (String string : strArr) {
						list.add(string);
					}
				}
			} else {
				list.add(specialString);
			}
		}
		return list;
	}

	/**
	 * @Title: stringToList
	 * @Description: 将字符串根据分隔符转化为list
	 * @param specialString
	 * @param prefix
	 *            分隔符
	 * @return List<Integer>
	 */
	public static List<Integer> stringToIntList(String specialString,
			String prefix) {
		List<Integer> list = null;
		if (isNotBlank(specialString) && isNotBlank(prefix)) {
			specialString = trimBeforePrefix(specialString, prefix);
			list = new ArrayList<Integer>();
			if (specialString.indexOf(prefix) > 0) {
				String[] strArr = specialString.split(prefix);
				if (null != strArr && strArr.length > 0) {
					for (String string : strArr) {
						if (isNumber(string))
							list.add(Integer.parseInt(string));
					}
				}
			} else {
				if (isNumber(specialString)) {
					list.add(Integer.parseInt(specialString));
				}
			}
		}
		if (null != list && list.size() == 0) {
			list = null;
		}
		return list;
	}

	/**
	 * @Title: stringToList
	 * @Description: 将字符串根据分隔符转化为list
	 * @param specialString
	 * @param prefix 分隔符
	 * @return List<Integer>
	 */
	public static List<Long> stringToLongList(String specialString,String prefix) {
		List<Long> list = null;
		if (isNotBlank(specialString) && isNotBlank(prefix)) {
			specialString = trimBeforePrefix(specialString, prefix);
			list = new ArrayList<Long>();
			if (specialString.indexOf(prefix) > 0) {
				String[] strArr = specialString.split(prefix);
				if (null != strArr && strArr.length > 0) {
					for (String string : strArr) {
						if (isNumber(string))
							list.add(Long.parseLong(string));
					}
				}
			} else {
				if (isNumber(specialString)) {
					list.add(Long.parseLong(specialString));
				}
			}
		}
		return list;
	}

	/**
	 * @Title: trimBeforePrefix
	 * @Description: 去掉前面的prefix
	 * @param specialString
	 * @param prefix
	 * @return String
	 */
	public static String trimBeforePrefix(String specialString, String prefix) {
		if (specialString.indexOf(prefix) == 0) {
			specialString = specialString.substring(1, specialString.length());
		}
		if (specialString.indexOf(prefix) == 0) {
			specialString = trimBeforePrefix(specialString, prefix);
		}
		specialString = trimRepetChar(specialString, prefix, prefix);
		return specialString;
	}

	/**
	 * @Title: trimRepetChar
	 * @Description: 替换多个重复的字符
	 * @param specialString
	 *            原字符串
	 * @param prefix
	 *            重复字符
	 * @param replacement
	 *            替换成的字符
	 * @return String
	 */
	public static String trimRepetChar(String specialString, String prefix,
			String replacement) {
		return specialString.replaceAll("[" + prefix + "]+", replacement);
	}

	/**
	 * @author li tao
	 * @Title: isNotBlank
	 * @Description: TODO 批量判断字符串是否是null或者""
	 * @param @param str 字符串
	 * @param @return 是null或者""返回false,否则返回true
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isNotBlank(String... str) {
		for (int i = 0; i < str.length; i++) {
			if (null == str[i] || "".equals(str[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @author zhangkui
	 * @Title: getLastString
	 * @Description: 获取信鸽返回信息拼接结果
	 * @param a_str1
	 * @param i_str2
	 * @return String
	 */
	public static String getLastString(String a_str1, String i_str2) {
		StringBuilder r_str = new StringBuilder();
		if (isNotBlank(a_str1)) {
			r_str.append("android:" + a_str1);
		}
		if (isNotBlank(i_str2)) {
			r_str.append(" ios:" + i_str2);
		}
		return r_str.toString();
	}

	/**
	 * @author zhangkui
	 * @Title: getlastInteger
	 * @Description: 获取信鸽返回结果编号
	 * @param a_code
	 * @param i_code
	 * @return Integer
	 */
	public static Integer getlastInteger(Integer a_code, Integer i_code) {
		Integer code = null;
		if (a_code != null && i_code != null) {
			code = a_code;
		}
		if (a_code != null && i_code == null) {
			code = a_code;
		}
		if (i_code != null && a_code == null) {
			code = i_code;
		}
		return code;
	}

	/**
	 * @Description: 判断字符串是否有非法字符
	 * @param tags
	 *            设定文件
	 * @author lichengbing
	 * @return int 返回类型 0:传入字符串为空 1： 含有非法字符 2：不含非法字符
	 * @throws
	 */
	public static int isIllegalString(String str) {
		int res = 0;
		if (isNotBlank(str)) {
			res = 2;
			char ch[] = { '%', '*', '/', '(', ')', '=', '<', '>', '!', '@',
					';', '[', ']', '{', '}', '?', '^', '#', '&', '￥', '，', '’',
					'‘', '；', '-', '+', '_', '$' };
			for (int i = 0, len = str.length(); i < len; i++) {
				// 传入字符串的某一个字符
				char c = str.charAt(i);
				for (int j = 0, len2 = ch.length; j < len2; j++) {
					char v = ch[j];
					if (c == v) {
						res = 1;
					}
				}
			}
		}
		return res;
	}

	/**
	 * @author li tao
	 * @Title: checkIds
	 * @Description: TODO 判断字符串是否可以转换为数字
	 * @param @param str 字符串
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean checkIds(String str) {
		if (isBlank(str)) {
			return false;
		}
		String ids = str.replace(",", "");
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(ids).matches();
	}

	/**
	 * @author zhao peng
	 * @Title: isNumeric
	 * @Description: 批量判断字符串是否是数字
	 * @param @param str
	 * @return boolean 返回类型
	 */
	public static boolean isNumeric(String... str) {
		for (int i = 0; i < str.length; i++) {
			if (str[i] == null) {
				return false;
			}
		}
		Pattern pattern = Pattern.compile("-?[0-9]+");
		for (int i = 0, len = str.length; i < len; i++) {
			Matcher isNum = pattern.matcher(str[i]);
			if (!isNum.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @author zhao peng
	 * @Title: isNumeric
	 * @Description: 判断字符串是否为字母
	 * @param @param str
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 */
	public static boolean isWord(String str) {
		if (isBlank(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isNumber(String str) {
		boolean flag = false;
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			flag = true;
		}
		return flag;
	}

	public static boolean isBlank(String... str) {
		for (int i = 0; i < str.length; i++) {
			if (isBlank(str[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author li tao
	 * @Title: filterStr
	 * @Description: 过滤不是字符.字母数字的
	 * @param @param str
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 */
	public static String filterStr(String s) {
		StringBuilder sb = new StringBuilder();
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if ((chars[i] >= 19968 && chars[i] <= 40869)
					|| (chars[i] >= 97 && chars[i] <= 122)
					|| (chars[i] >= 65 && chars[i] <= 90)) {
				sb.append(chars[i]);
			}
			if (isNumeric(chars[i] + "")) {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

}
