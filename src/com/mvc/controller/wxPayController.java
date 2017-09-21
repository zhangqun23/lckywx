package com.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import com.mvc.constants.wxPayConstants;
import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;
import com.mvc.service.TravelService;
import com.utils.SessionUtil;
import com.utils.StringUtil;
import com.utils.wxPayUtil;

@Controller
@RequestMapping("/pay")
public class wxPayController {
	
	@Autowired
	TravelService travelService;

	@RequestMapping("/travelPay.do")
	public @ResponseBody String travelPay(HttpServletRequest request, HttpServletResponse responest) throws Exception{
		
		String openid = SessionUtil.getOpenid(request);
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("payNeed"));
		String travelid = request.getParameter("travelid");
		Integer trtr_mnum = 0;
		Integer trtr_cnum = 0;
		String trtr_tel = null;
		if (jsonObject.containsKey("trtr_mnum")) {
			if(StringUtil.strIsNotEmpty(jsonObject.getString("trtr_mnum"))){
				trtr_mnum = Integer.parseInt(jsonObject.getString("trtr_mnum"));
			}
		}
		if (jsonObject.containsKey("trtr_cnum")) {
			if(StringUtil.strIsNotEmpty(jsonObject.getString("trtr_cnum"))){
				trtr_cnum = Integer.parseInt(jsonObject.getString("trtr_cnum"));
			}
		}
		if (jsonObject.containsKey("trtr_tel")) {
			if(StringUtil.strIsNotEmpty(jsonObject.getString("trtr_tel"))){
				trtr_tel = jsonObject.getString("trtr_tel");
			}	
		}

		Travel travel = travelService.findTravelById(travelid);
		
		JSONObject json = new JSONObject();
		if(trtr_mnum+trtr_cnum>travel.getTravel_left_num()){
			String e = "剩余票数不足";
			json.put("e", e);
			return json.toString();
		}
		String attach = travel.getTravel_title();
		Float mprice = travel.getTravel_mprice();
		Float cprice = travel.getTravel_cprice();
		String out_trade_no = wxPayUtil.getTradeNo();
		float total_fee = (trtr_mnum*mprice+trtr_cnum*cprice)*travel.getTravel_discount()+(trtr_mnum+trtr_cnum)*travel.getTravel_insurance();
		
		TravelTrade travelTrade = new TravelTrade();;
		travelTrade.setTravel(travel);
		travelTrade.setTrtr_cnum(trtr_cnum);
		travelTrade.setTrtr_mnum(trtr_mnum);
		travelTrade.setTrtr_price(total_fee);
		travelTrade.setTrtr_tel(trtr_tel);
		travelTrade.setTrtr_num(out_trade_no);
		travelTrade.setOpen_id(openid);
		travelTrade.setTrade_discount(travel.getTravel_discount());
		travelTrade.setIs_state(1);
		travelService.saveTravelTrade(travelTrade);
		
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("attach", attach);
		paraMap.put("total_fee", String.valueOf(total_fee*100));
		paraMap.put("body", wxPayConstants.TRAVELBODY);
		String result = jspay(paraMap, request, responest);
		json.put("total_fee", total_fee);
		json.put("out_trade_no", out_trade_no);
		return json.toString();
	}

	@RequestMapping("/requestPay.do")
	public @ResponseBody String jspay(Map paraMap, HttpServletRequest request, HttpServletResponse responest) throws Exception{
		return null;
		
/*		String openid = SessionUtil.getOpenid(request);
		String out_trade_no = wxPayUtil.getTradeNo();
		String nonce_str = wxPayUtil.create_nonce_str();
		//获取沙箱秘钥，沙箱测试说明：金额必须为101；之后使用的key值都为沙箱秘钥；前台js调用接口不能成功，会显示缺少参数total_fee
		//String sandbox_signkey = doGetSandboxSignKey(wxPayConstants.MCH_ID, wxPayConstants.PATERNERKEY);
		
		Map<String, String> paraMap = new HashMap<String, String>();
		//此部分为设置订单参数，部分信息需要前台穿过来,有*为必填
		paraMap.put("appid", wxPayConstants.APPID);//* 
		paraMap.put("mch_id", wxPayConstants.MCH_ID); //*商户号
		paraMap.put("nonce_str", nonce_str); //*随机字符串
		paraMap.put("sign_type", wxPayConstants.SIGNTYPE);//签名类型，默认为MD5，支持HMAC-SHA256和MD5。
		paraMap.put("body", wxPayConstants.BODY); //*body字段
		paraMap.put("attach", attach); //附加数据
		paraMap.put("openid", openid); //*用户信息
		paraMap.put("out_trade_no", out_trade_no); //*商户订单号
		paraMap.put("fee_type", "CNY");//标价币种
		paraMap.put("total_fee", String.valueOf(total_fee*100)); //*订单总金额，单位为分（需前台传来）
		paraMap.put("spbill_create_ip", wxPayUtil.getAddrIp(request)); //*获取用户IP地址，APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
		paraMap.put("trade_type", "JSAPI"); //*调用接口提交的交易类型，取值如下：JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
		paraMap.put("notify_url", wxPayConstants.NOTIFY_URL); //*回调URL
		String sign = wxPayUtil.generateSignature(paraMap, wxPayConstants.PATERNERKEY);
		paraMap.put("sign", sign); //*签名
		
		// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder ,沙盒测试加sandboxnew
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		//参数值用XML转义即可，CDATA标签用于说明数据不被XML解析器解析.detail不被XML解析
		String xml = wxPayUtil.mapToXml(paraMap);
		//异步发送请求
		String xmlStr = HttpKit.post(url, xml);
		//预支付交易会话标识 
		String prepay_id = "";
		Map<String, String> map = new HashMap<String, String>();
		if (xmlStr.indexOf("SUCCESS") != -1) { 
			map = wxPayUtil.xmlToMap(xmlStr); 
			prepay_id = (String) map.get("prepay_id"); 
		}
		
		String timeStamp2 = wxPayUtil.getCurrentTimestamp();
		String nonceStr2 = wxPayUtil.create_nonce_str();
		Map<String, String> payMap = new HashMap<String, String>();
		payMap.put("appId", (String) map.get("appid"));
		payMap.put("nonceStr", nonceStr2);
		payMap.put("timeStamp", timeStamp2);
		payMap.put("signType", "MD5");
		payMap.put("package", "prepay_id=" + prepay_id);
		
		String paySign = wxPayUtil.generateSignature(payMap,wxPayConstants.PATERNERKEY);
		JSONObject json = new JSONObject();
		json.put("appId", (String) map.get("appid"));
		json.put("timeStamp", timeStamp2);
		json.put("nonceStr", nonceStr2);
		json.put("pg", prepay_id);
		json.put("signType", wxPayConstants.SIGNTYPE);
		json.put("paySign", paySign);
		json.put("out_trade_no",out_trade_no);
		json.put("total_fee",total_fee);
		System.out.println(json.toString());
		return json.toString();*/
	}
	
		
}
