package org.platform.pay.common.alipay.pcOrapp;

import java.io.IOException;
import java.util.Properties;
import org.platform.pay.common.util.QueryKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支付宝支付
 * @author otvcloud
 */
public class AlipayConstants {
	
	private static Logger LOG = LoggerFactory.getLogger(AlipayConstants.class);
	
	/**appid**/
	public static String appid;
	/**私钥**/
	public static String app_private_key;
	/**公钥**/
	public static String alipay_public_key;
	
	public static String notify_url;
	
	static{
		Properties properties = new Properties();
		try {
			properties.load(AlipayConstants.class.getResourceAsStream("/alipay.properties"));
		} catch (IOException e) {
			LOG.error("没有找到alipay.properties文件,error:", e);
		}
		appid = properties.getProperty("appid");
		notify_url = properties.getProperty("notify.url");
		app_private_key = QueryKey.getKey("/rsa_private_key_pkcs8.pem");
		alipay_public_key = QueryKey.getKey("/rsa_public_key.pem");
	}
	
	
}
