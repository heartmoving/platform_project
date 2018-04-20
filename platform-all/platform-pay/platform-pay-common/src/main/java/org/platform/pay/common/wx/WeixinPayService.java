package org.platform.pay.common.wx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.platform.base.common.util.RandomStringGenerator;
import org.platform.pay.common.wx.config.BaseReqData;
import org.platform.pay.common.wx.config.Constants;
import org.platform.pay.common.wx.config.HttpUtils;
import org.platform.pay.common.wx.config.WeixinUtils;
import org.platform.pay.common.wx.config.WxProperties;
import org.platform.pay.common.wx.config.XMLParser;
import org.platform.pay.common.wx.config.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 微信支付工具
 * @author zhang kui
 */
public class WeixinPayService {
	
	private static Logger LOG = LoggerFactory.getLogger(WeixinPayService.class);
	/**
	 * 微信支付二维码连接
	 * @param productId
	 * @return
	 */
	public static String paySmQrCodeUrl(String productId){
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String noceStr = RandomStringGenerator.getRandomStringByLength(32);
		String timeStamp = String.valueOf((System.currentTimeMillis() / 1000));
		finalpackage.put("appid", WxProperties.getValue("appid"));  
		finalpackage.put("mch_id", WxProperties.getValue("appmch_id"));  
		finalpackage.put("time_stamp", timeStamp);  
		finalpackage.put("nonce_str", noceStr);  
		finalpackage.put("product_id", productId);  
		String sign = WeixinUtils.createSign(finalpackage);
		StringBuilder urlBuilder = new StringBuilder("weixin://wxpay/bizpayurl?");
		urlBuilder.append("appid=").append(WxProperties.getValue("appid")).append("&");
		urlBuilder.append("mch_id=").append(WxProperties.getValue("appmch_id")).append("&");
		urlBuilder.append("product_id=").append(productId).append("&");
		urlBuilder.append("time_stamp=").append(timeStamp).append("&");
		urlBuilder.append("nonce_str=").append(noceStr).append("&");
		urlBuilder.append("sign=").append(sign);
		return urlBuilder.toString();
	}
	
	/**
	 * 扫码验证签名
	 * @param xml 用户扫描后，返回的xml 信息
	 * @return
	 */
	public static boolean isSign(Map<String, String> params){
		String sign = params.get("sign");
		SortedMap<String, String>  sortedMap =	sortMap(params);
		String newsign = WeixinUtils.createSign(sortedMap);
		return newsign.equals(sign);
	}
	
	/**
	 * 排序map
	 * @param params
	 * @return
	 */
	public static SortedMap<String, String> sortMap(Map<String, String> params){
		SortedMap<String, String> sortmap = new TreeMap<String, String>();
		Set<Entry<String, String>> entries = params.entrySet();
		Iterator<Entry<String, String>> iterator = entries.iterator();
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			sortmap.put(entry.getKey(), entry.getValue());
		}
		return sortmap;
	}
	
	/**
	 * 扫码订单生成
	 * @param params
	 * @return
	 */
	public static String smOrderXml(Double order_price,String orderNo,String product_name,String ip){
		Integer orderPrice = (int)(order_price * 100);
		Map<String, String> map = WeixinUtils.payUnifiedorder(product_name,orderPrice, ip, orderNo,null);
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		finalpackage.put("return_code", "FAIL");
		if (null != map) {
			finalpackage.put("return_code", "SUCCESS");
			String app_id = map.get("appid");
			/**获取预支付id**/
			String prepayid = map.get("prepay_id");
			/**
			 * 创建预支付sign签名
			 */
			String noceStr = RandomStringGenerator.getRandomStringByLength(32);
			String timeStamp = String.valueOf((System.currentTimeMillis() / 1000));
			finalpackage.put("appid", app_id);  
			finalpackage.put("mch_id", WxProperties.getValue("appmch_id"));  
			finalpackage.put("nonce_str", noceStr);  
			finalpackage.put("prepay_id", prepayid);  
			finalpackage.put("result_code", "SUCCESS");
			String sign = WeixinUtils.createSign(finalpackage);
			finalpackage.put("sign", sign);
		}
		return XMLUtil.getXml(finalpackage);
	}
	
	
	/**
	 * 微信js支付
	 * @param order_price
	 * @param orderNo
	 * @param product_name
	 * @param ip
	 * @return
	 */
	public static Map<String, Object> weixinJsPayStr(Double order_price,String orderNo,String product_name,String ip,String openId){
		Integer orderPrice = (int)(order_price * 100);
		Map<String, String> map = WeixinUtils.payJsUnifiedorder(product_name,orderPrice, ip, orderNo,openId);
		TreeMap<String, Object> outParams = new TreeMap<String, Object>();
		if (null != map) {
			String app_id = map.get("appid");
			/**获取预支付id**/
			String prepayid = map.get("prepay_id");
			/**
			 * 创建预支付sign签名
			 */
			String noceStr = RandomStringGenerator.getRandomStringByLength(32);
			String timeStamp = String.valueOf((System.currentTimeMillis() / 1000));
			SortedMap<String, String> finalpackage = new TreeMap<String, String>();
			finalpackage.put("appId", app_id);  
			finalpackage.put("timeStamp", timeStamp);  
			finalpackage.put("nonceStr", noceStr);  
			finalpackage.put("package", "prepay_id="+prepayid);  
			finalpackage.put("signType", "MD5");
			String sign = WeixinUtils.createSign(finalpackage);
			/**
			 * 返回手机端支付需要的全部参数
			 */
			outParams.put("appId", Constants.APP_ID);
			outParams.put("nonceStr", noceStr);
			outParams.put("package", "prepay_id="+prepayid);
			outParams.put("timeStamp", timeStamp);
			outParams.put("signType", "MD5");
			outParams.put("paySign", sign);
		}
		return outParams;
	}
	
	/**
	 * 微信js支付
	 * @param order_price
	 * @param orderNo
	 * @param product_name
	 * @param ip
	 * @return
	 */
	public static Map<String, Object> weixinMinAppPayStr(Double order_price,String orderNo,String product_name,String ip,String openId){
		Integer orderPrice = (int)(order_price * 100);
		Map<String, String> map = WeixinUtils.payMinAppUnifiedorder(product_name,orderPrice, ip, orderNo,openId);
		TreeMap<String, Object> outParams = new TreeMap<String, Object>();
		if (null != map) {
			String app_id = map.get("appid");
			/**获取预支付id**/
			String prepayid = map.get("prepay_id");
			/**
			 * 创建预支付sign签名
			 */
			String noceStr = RandomStringGenerator.getRandomStringByLength(32);
			String timeStamp = String.valueOf((System.currentTimeMillis() / 1000));
			SortedMap<String, String> finalpackage = new TreeMap<String, String>();
			finalpackage.put("appId", app_id);  
			finalpackage.put("timeStamp", timeStamp);  
			finalpackage.put("nonceStr", noceStr);  
			finalpackage.put("package", "prepay_id="+prepayid);  
			finalpackage.put("signType", "MD5");
			String sign = WeixinUtils.createSign(finalpackage);
			/**
			 * 返回手机端支付需要的全部参数
			 */
			outParams.put("appId", app_id);
			outParams.put("nonceStr", noceStr);
			outParams.put("package", "prepay_id="+prepayid);
			outParams.put("timeStamp", timeStamp);
			outParams.put("signType", "MD5");
			outParams.put("paySign", sign);
		}
		return outParams;
	}
	
	
	/**
	 * 排序map
	 * @param map
	 * @return
	 */
	public static String callbackSign(Map<String, String> map){
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		Set<Entry<String, String>> entries = map.entrySet();
		Iterator<Entry<String, String>> iterator = entries.iterator();
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			finalpackage.put(entry.getKey(), entry.getValue());
		}
		return WeixinUtils.createSign(finalpackage);
	}
	
	/**
	 * 验证签名
	 * @param map
	 * @return
	 */
	public static boolean isVidateSign(Map<String, String> map){
		String sign = callbackSign(map);
		return sign.equals(map.get("sign"));
	}
	
	
	/**
	 * 查询订单是支付状态
	 * @param out_trade_no
	 * @return
	 */
	public static Map<String, String> orderquery(String out_trade_no){
		Map<String, String> map = null;
		try{
			String qryUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
			String noceStr = RandomStringGenerator.getRandomStringByLength(32);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appid", WxProperties.getValue("appid"));
			paramMap.put("mch_id", WxProperties.getValue("appmch_id"));
			paramMap.put("out_trade_no", out_trade_no);
			paramMap.put("nonce_str", noceStr);
			paramMap.put("sign_type", "MD5");
			String sign = callbackSign(paramMap);
			paramMap.put("sign", sign);
			String xml = BaseReqData.toXml(paramMap);
			String res = HttpUtils.postXml(qryUrl,xml);
			map = XMLParser.getMapFromXML(res);
		}catch(Exception e){
			LOG.error("查询订单状态错误,error:", e);
		}
		return map;
	}
	
}
