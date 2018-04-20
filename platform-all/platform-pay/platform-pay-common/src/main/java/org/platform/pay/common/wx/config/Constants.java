package org.platform.pay.common.wx.config;


public class Constants {
	

	/**微信支付功能**/
	
	/**
	 * 商户号
	 */
	public static final String  MCH_ID ;

	/**
	 * appi
	 */
	public static final String APP_ID ;
	
	/**
	 * key
	 */
	public static final String KEY ;
	
	/**
	 * app_secret
	 */
//	public static final String APP_SECRET = "871323256d118aec0e6e28a773d18cca";
	 
	/**
	 *  支付完成后的回调处理页面		配置见config配置文件
	 */
	public static String NOTIFY_URL;
	
	public static String PAGE_URL;
	
	

	static {
		/*Properties props = new Properties();
		try {
			props.load(Constants.class.getResourceAsStream("wx.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		NOTIFY_URL = WxProperties.getValue("NOTIFY_URL");
		PAGE_URL = WxProperties.getValue("RETURN_PAGE_URL"); 
		MCH_ID = WxProperties.getValue("appmch_id");
		APP_ID = WxProperties.getValue("appid");
		KEY = WxProperties.getValue("appmch_key");
	}
	// 以下是几个API的路径：
	// 1）统一下单
	public static String PAY_UNFIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	// 2）被扫支付查询API
	public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";
	 
}
