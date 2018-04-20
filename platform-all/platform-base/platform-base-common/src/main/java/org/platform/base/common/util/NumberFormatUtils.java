package org.platform.base.common.util;

import java.text.DecimalFormat;
/**
 * @Title: NumberFormatUtils.java
 * @Package org.plaform.base.common.util
 * @Description: TODO 金额数字格式工具类
 * @author zhang kui
 * @date 2014-12-19 下午12:53:42
 * @version V1.0
 */
public class NumberFormatUtils {
  
	/**
	 * 格式化金额
	 * @param amount
	 * @return
	 */
	public static String formatAmount(Double amount){
		DecimalFormat decimalFormat = new DecimalFormat("##.00");
		if(amount<1){
			return "0"+decimalFormat.format(amount);
		}
		return decimalFormat.format(amount);
	}
	
}
