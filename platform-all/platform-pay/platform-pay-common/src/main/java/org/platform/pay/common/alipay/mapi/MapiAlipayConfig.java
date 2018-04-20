package org.platform.pay.common.alipay.mapi;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.platform.pay.common.util.QueryKey;

import com.alipay.api.AlipayConstants;

/**
 * mapi 手机网页支付配置信息
 * @author otvcloud
 */
public class MapiAlipayConfig {
	
		/**日志打印**/
		private static Logger LOG = Logger.getLogger(MapiAlipayConfig.class);
		//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

		// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
		public static String partner = "";
		
		// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
		public static String seller_id = partner;

		// MD5密钥，安全检验码，由数字和字母组成的32位字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	    public static String key = "";

		// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://xxz/alipay.wap.create.direct.pay.by.user-JAVA-UTF-8/notify_url.jsp";

		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String return_url = "http://xzzx/alipay.wap.create.direct.pay.by.user-JAVA-UTF-8/return_url.jsp";

		// 签名方式
		public static String sign_type = "MD5";
		
		// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
		public static String log_path = "C:\\";
			
		// 字符编码格式 目前支持utf-8
		public static String input_charset = "utf-8";
			
		// 支付类型 ，无需修改
		public static String payment_type = "1";
			
		// 调用的接口名，无需修改
		public static String service = "alipay.wap.create.direct.pay.by.user";

		public static String show_url = "";
		/**mapi 私有key**/
		public static String mapiapp_private_key = "";
		/**mapi 公有key**/
		public static  String mapialipay_public_key = "";
		
		static{
			Properties properties = new Properties();
			try {
				properties.load(AlipayConstants.class.getResourceAsStream("/alipay.properties"));
			} catch (IOException e) {
			}
			notify_url = properties.getProperty("notify.url.mapi");
			partner =  properties.getProperty("partner");
			seller_id =  properties.getProperty("partner");
			key = properties.getProperty("md5_key");
			return_url = properties.getProperty("return_url");
			show_url = properties.getProperty("show_url");
			mapiapp_private_key = QueryKey.getKey("/mapirsa_private_key.pem");
			mapialipay_public_key = QueryKey.getKey("/mapirsa_public_key.pem");
		}
		//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
		
}
