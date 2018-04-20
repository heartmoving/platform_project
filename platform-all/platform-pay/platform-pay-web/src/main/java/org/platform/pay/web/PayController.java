package org.platform.pay.web;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.platform.base.common.util.StringUtils;
import org.platform.pay.common.alipay.AlipayUtil;
import org.platform.pay.common.alipay.mapi.AlipayNotify;
import org.platform.pay.common.wx.WeixinPayService;
import org.platform.pay.common.wx.config.XMLUtil;
import org.platform.pay.response.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付相关的信息
 * @author otvcloud
 */
@RestController
@RequestMapping("/pay")
public class PayController extends BaseController {
	
	private Logger LOG = Logger.getLogger(PayController.class);
	/**
	 * 微信回掉地址
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("returnPay")
	@ResponseBody
	public BaseResponse<Map<String, String>> returnPay(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<Map<String, String>> out = BaseResponse.success();
		try{
			InputStream inputStream = request.getInputStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] bu = new byte[10240];
			int num = -1;
			while((num=inputStream.read(bu))!=-1){
				outputStream.write(bu, 0, num);
			}
			outputStream.close();
			inputStream.close();
			String str = new String(outputStream.toByteArray(),"utf-8");
			LOG.info("支付回调字符串:"+str);
			Map<String, String> map = XMLUtil.doXMLParse(str);
			if(!WeixinPayService.isVidateSign(map)){
				out.setCode(-1);
				out.setMessage("签名失败");
				return out;
			}
			out.setData(map);
		}catch(Exception e){
			out = BaseResponse.fail("支付失败");
			LOG.error("支付成功回调错误，"+e.getMessage());
		}
		return out;
	}
	
	
	/**
	 * 获取用户js支付信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("jsPay")
	@ResponseBody
	public BaseResponse<Map<String, Object>>  pay(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<Map<String, Object>> out = BaseResponse.success();
		try{
			String type = request.getParameter("type");
			//product.getPrice(), jsorder.getOrderNo(), product.getProductName(), IpUtil.getIpAddr(request), openid
			String orderPrice = request.getParameter("orderPrice");
			String orderNo = request.getParameter("orderNo");
			String orderName = request.getParameter("orderName");
			String ip = request.getParameter("ip");
			String openid = request.getParameter("openid");
			if(StringUtils.isBlank(type) || StringUtils.isBlank(orderPrice) || StringUtils.isBlank(orderName)
					|| StringUtils.isBlank(ip) || StringUtils.isBlank(openid)){
				return BaseResponse.fail("type,orderPrice,orderNo,orderName,ip,openid  必须传入");
			}
			if("weixin".equals(type)){
				out.setData(WeixinPayService.weixinMinAppPayStr(Double.parseDouble(orderPrice), orderNo, orderName, ip, openid)); 
			}else{
				out.setData(WeixinPayService.weixinJsPayStr(Double.parseDouble(orderPrice), orderNo, orderName, ip, openid));
			}
		}catch(Exception e){
			out = BaseResponse.fail("js支付下单失败");
			LOG.error("js支付下单失败，"+e.getMessage());
		}
		return out;
	}
	
	/**
	 * 获取用户js支付信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("alipayPay")
	@ResponseBody
	public BaseResponse<Map<String, Object>>  alipayPay(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<Map<String, Object>> out = BaseResponse.success();
		try{
			String orderPrice = request.getParameter("orderPrice");
			String orderNo = request.getParameter("orderNo");
			String orderName = request.getParameter("orderName");
			if(StringUtils.isBlank(orderPrice) || StringUtils.isBlank(orderNo) || StringUtils.isBlank(orderName)){
				return BaseResponse.fail("orderPrice,orderNo,orderName");
			}
			String htmlSub = AlipayUtil.payMapi(orderNo, orderPrice, orderName, "1", "120m");
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("payUrl", htmlSub);
			out.setData(returnMap);
		}catch(Exception e){
			out = BaseResponse.fail("支付宝下单失败");
			LOG.error("支付宝下单失败，"+e.getMessage());
		}
		return out;
	}
	
	
	/**
	 * 支付宝回调地址
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("alipayReturnPay")
	public BaseResponse<Map<String, String>> alipayReturnPay(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<Map<String, String>> out = BaseResponse.success();
		Enumeration<String> enumeration = request.getParameterNames();
		try{
			TreeMap<String, String> param = new TreeMap<String, String>();
			while(enumeration.hasMoreElements()){
				String key = enumeration.nextElement();
				String value = request.getParameter(key);
				param.put(key, value);
			}
			LOG.info("回调参数"+param);
			/**验证签名**/
			if(!AlipayUtil.isVerificationSign(param)){
				out.setCode(-1);
				out.setMessage("签名失败");
				return out;
			}
			LOG.info("验证签名成功---------------:"+param.get("trade_status"));
					/**回调处理业务逻辑**/
			out.setData(param);
		}catch(Exception e){
			out = BaseResponse.fail("支付失败");
			LOG.error("支付宝回调错误，error:", e);
		}
		return out;
	}
	
	/**
	 * mapi支付宝回调地址
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("alipayReturnPayMapi")
	public BaseResponse<Map<String, String>> alipayReturnPayMapi(HttpServletRequest request,HttpServletResponse response){
		BaseResponse<Map<String, String>> out = BaseResponse.success();
		try{
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			if(!AlipayNotify.verify(params)){//验证成功
				return BaseResponse.fail("签名失败");
			}
			out.setData(params);
		}catch(Exception e){
			out = BaseResponse.fail("支付失败");
			LOG.error("支付宝回调错误，error:", e);
		}
		return out;
	}
	
}
