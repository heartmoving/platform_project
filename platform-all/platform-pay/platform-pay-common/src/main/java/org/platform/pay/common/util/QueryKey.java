package org.platform.pay.common.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.platform.pay.common.alipay.AlipayUtil;

/**
 * 查询key
 * @author otvcloud
 */
public class QueryKey {

	private static Logger LOG = Logger.getLogger(QueryKey.class);
	
	/**
	 * 获取密钥
	 * @param path
	 * @return
	 */
	public static String getKey(String path){
		InputStream inputStream = AlipayUtil.class.getResourceAsStream(path);
		String key ="";
		try {
			byte[] s = new byte[1024];
			int n = -1;
			while((n=inputStream.read(s)) != -1){
				key = key + new String(s,0,n);
			}
			inputStream.close();
		} catch (IOException e) {
			LOG.error("支付密钥key", e);
		}
		return key;
	}
}
