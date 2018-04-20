package org.platform.pay.common.alipay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.platform.pay.common.alipay.mapi.AlipaySubmit;
import org.platform.pay.common.alipay.mapi.MapiAlipayConfig;
import org.platform.pay.common.alipay.pcOrapp.AlipayConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

/**
 * 支付宝扫描支付获取二维码
 * @author otvcloud
 */
public class AlipayUtil {
	
	private static Logger LOG = LoggerFactory.getLogger(AlipayUtil.class);

	/**
	 * 获取支付二维码
	 * @param out_trade_no 订单号
	 * @param total_amount 订单金额（元）
	 * @param subject 订单标题
	 * @param store_id 商户门店编号
	 * @param timeout_express 交易超时时间
	 * @return
	 */
	public static String payQrCode(String out_trade_no,String total_amount,String subject,String store_id,String timeout_express){
		try {
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AlipayConstants.appid,AlipayConstants.app_private_key,"json","UTF-8",AlipayConstants.alipay_public_key);
			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"out_trade_no\"").append(":\"").append(out_trade_no).append("\"").append(",");
			builder.append("\"total_amount\"").append(":").append(total_amount).append(",");
			builder.append("\"subject\"").append(":\"").append(subject).append("\"").append(",");
			builder.append("\"store_id\"").append(":\"").append(store_id).append("\"").append(",");
			builder.append("\"timeout_express\"").append(":\"").append(timeout_express).append("\"").append("}");
			request.setBizContent(builder.toString());//设置业务参数
			request.setNotifyUrl(AlipayConstants.notify_url);/**异步通知url**/
			AlipayTradePrecreateResponse response = alipayClient.execute(request);
			System.out.println(response.getQrCode());
			return response.getQrCode();
		} catch (AlipayApiException e) {
			e.printStackTrace();
			LOG.error("获取支付二维码错误：error",e);
		}
		return null;
	}
	
	
	/**
	 * 验证回调签名
	 * @param treeMap 验证签名
	 * @return
	 */
	public static boolean isVerificationSign(TreeMap<String, String> treeMap)throws Exception{
		StringBuilder builder = new StringBuilder();
		Set<Entry<String, String>> entries = treeMap.entrySet();
		Iterator<Entry<String, String>> iterator = entries.iterator();
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			String key = entry.getKey();
			if(!("sign".equals(key)) && !("sign_type".equals(key))){
				builder.append(key).append("=").append(entry.getValue()).append("&");
			}
		}
		String signStr = builder.substring(0, builder.length() -1);
		String sign = treeMap.get("sign");
		boolean b= AlipaySignature.rsaCheck(signStr, sign, AlipayConstants.alipay_public_key, "UTF-8", "RSA");
		return b;
	}
	
	/**
	 * 获取支付二维码
	 * @param out_trade_no 订单号
	 * @param total_amount 订单金额（元）
	 * @param subject 订单标题
	 * @param store_id 商户门店编号
	 * @param timeout_express 交易超时时间
	 * @return
	 */
	public static String payWap(String out_trade_no,String total_amount,String subject,String store_id,String timeout_express){
		try {
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",AlipayConstants.appid,AlipayConstants.app_private_key,"json","UTF-8",AlipayConstants.alipay_public_key,"RSA2");
			AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request类
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"out_trade_no\"").append(":\"").append(out_trade_no).append("\"").append(",");
			builder.append("\"total_amount\"").append(":").append(total_amount).append(",");
			builder.append("\"subject\"").append(":\"").append(subject).append("\"").append(",");
			builder.append("\"store_id\"").append(":\"").append(store_id).append("\"").append(",");
			builder.append("\"product_code\"").append(":\"").append("QUICK_WAP_PAY").append("\"").append(",");
			builder.append("\"timeout_express\"").append(":\"").append(timeout_express).append("\"").append("}");
			alipayRequest.setBizContent(builder.toString());//设置业务参数
			alipayRequest.setNotifyUrl(AlipayConstants.notify_url);/**异步通知url**/
			return alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			LOG.error("获取支付二维码错误：error",e);
		}
		return null;
	}
	
	/**
	 * mapi支付
	 * @param out_trade_no
	 * @param total_amount
	 * @param subject
	 * @param store_id
	 * @param timeout_express
	 * @return
	 */
	public static String payMapi(String out_trade_no,String total_amount,String subject,String store_id,String timeout_express){
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", MapiAlipayConfig.service);
	    sParaTemp.put("partner", MapiAlipayConfig.partner);
	    sParaTemp.put("seller_id", MapiAlipayConfig.seller_id);
	    sParaTemp.put("_input_charset", MapiAlipayConfig.input_charset);
		sParaTemp.put("payment_type", MapiAlipayConfig.payment_type);
		sParaTemp.put("notify_url", MapiAlipayConfig.notify_url);
		sParaTemp.put("return_url", MapiAlipayConfig.return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_amount);
		sParaTemp.put("show_url", MapiAlipayConfig.show_url);
		//sParaTemp.put("app_pay","Y");//启用此参数可唤起钱包APP支付。
		sParaTemp.put("body", subject);
		//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
	    //如sParaTemp.put("参数名","参数值");
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		return sHtmlText;
	}
	
	/**
	 * 支付宝查询订单，扫描是否是没支付
	 * @param out_trade_no
	 * @return
	 */
	public static Map<String, Object> orderQuery(String out_trade_no){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isPay", false);
		try{
			AlipayClient alipayClient = new DefaultAlipayClient("https://mapi.alipay.com/gateway.do",AlipayConstants.appid,MapiAlipayConfig.mapiapp_private_key,"json","UTF-8",MapiAlipayConfig.mapialipay_public_key,"RSA");
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizContent("{\"out_trade_no\":\""+out_trade_no+"\"}");
			AlipayTradeQueryResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				map.put("isPay", true);
				map.put("body", response.getBody());
				map.put("transactionId", response.getTradeNo());
			} 
		}catch(Exception e){
			LOG.error("支付宝支付查询错误,error：", e);
		}
		return map;
	}
	
}
