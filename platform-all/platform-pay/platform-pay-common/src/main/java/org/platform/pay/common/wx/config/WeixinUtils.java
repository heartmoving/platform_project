package org.platform.pay.common.wx.config;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.http.NameValuePair;
import org.platform.base.common.util.DateUtils;
import org.platform.pay.common.util.MD5;


public class WeixinUtils {

	public static String getOut_trade_no() {
		String out_trade_no = String.format("NO%s%s",
				DateUtils.formatDate(new Date(), DateUtils.LOCATE_DATE_FORMAT),
				getRandomNumber(10));
		return out_trade_no;
	}

	public static String getRandomNumber(int n) {
		if (n <= 0)
			return null;
		String str = "";
		for (int i = 0; i < n; i++) {
			str += (int) (Math.random() * 10);
		}
		return str;
	}

	/**
	 * 统一下单
	 * 
	 * @param name
	 *            商品名称
	 * @param total_fee
	 *            价格（单位：分）
	 * @param ip
	 *            ip
	 */
	public static Map<String, String> payUnifiedorder(String name,
			int total_fee, String ip, String out_trade_no,String openid) {
		UnifiedorderPayReqDate unifiedorderPayReqDate = new UnifiedorderPayReqDate(
				Constants.APP_ID, Constants.MCH_ID, "", name, "", "",
				out_trade_no, "", total_fee, ip, "", "", "",
				Constants.NOTIFY_URL, "NATIVE", "", openid,true);
		String postDataXML = unifiedorderPayReqDate.toXml();
		postDataXML = "\n" + postDataXML;
		System.out.println(postDataXML);

		try {
			String res = HttpUtils.postXml(Constants.PAY_UNFIFIEDORDER,
					postDataXML);
			System.out.println(res);
			Map<String, String> map = XMLParser.getMapFromXML(res);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	/**
	 * 统一下单
	 * 
	 * @param name
	 *            商品名称
	 * @param total_fee
	 *            价格（单位：分）
	 * @param ip
	 *            ip
	 */
	public static Map<String, String> payJsUnifiedorder(String name,
			int total_fee, String ip, String out_trade_no,String openid) {
		UnifiedorderPayReqDate unifiedorderPayReqDate = new UnifiedorderPayReqDate(
				Constants.APP_ID, Constants.MCH_ID, "", name, "", "",
				out_trade_no, "", total_fee, ip, "", "", "",
				Constants.NOTIFY_URL, "JSAPI", "", openid,true);
		String postDataXML = unifiedorderPayReqDate.toXml();
		postDataXML = "\n" + postDataXML;
		System.out.println(postDataXML);

		try {
			String res = HttpUtils.postXml(Constants.PAY_UNFIFIEDORDER,
					postDataXML);
			System.out.println(res);
			Map<String, String> map = XMLParser.getMapFromXML(res);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	public static Map<String, String> payMinAppUnifiedorder(String name,
			int total_fee, String ip, String out_trade_no,String openid) {
		UnifiedorderPayReqDate unifiedorderPayReqDate = new UnifiedorderPayReqDate(
				WxProperties.getValue("_wxappid"), WxProperties.getValue("_appmch_id"), "", name, "", "",
				out_trade_no, "", total_fee, ip, "", "", "",
				Constants.NOTIFY_URL, "JSAPI", "", openid,false);
		String postDataXML = unifiedorderPayReqDate.toXml();
		postDataXML = "\n" + postDataXML;

		try {
			String res = HttpUtils.postXml(Constants.PAY_UNFIFIEDORDER,
					postDataXML);
			Map<String, String> map = XMLParser.getMapFromXML(res);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Map<String, String> payOrderquery(String out_trade_no) {
		ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData(null,
				out_trade_no);
		String postDataXML = scanPayQueryReqData.toXml();
		postDataXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ postDataXML;
		try {
			String res = HttpUtils
					.postXml(Constants.PAY_QUERY_API, postDataXML);
			System.out.println(res);
			Map<String, String> map = XMLParser.getMapFromXML(res);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.KEY);
		System.out.println(sb.toString());
		String result = MD5.MD5Encode(sb.toString()).toUpperCase();
		return result;
	}
	
	/**
	 * 签名
	 * @param packageParams
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + Constants.KEY);
		String sign = MD5.MD5Encode(sb.toString())
				.toUpperCase();
		return sign;

	}
	
}
