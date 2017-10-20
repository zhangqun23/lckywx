package com.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import com.mvc.constants.wxPayConstants;
import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;
import com.mvc.service.TravelService;
import com.utils.HttpKit;
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
		Float mprice = travel.getTravel_mprice()*100;
		Float cprice = travel.getTravel_cprice()*100;
		String out_trade_no = wxPayUtil.getTradeNo();
		Float discount = travel.getTravel_discount();
		int total_fee;
		if(discount == 0 || discount == null){
			discount = (float) 1;
			total_fee = (int) ((trtr_mnum*mprice+trtr_cnum*cprice)*discount+(trtr_mnum+trtr_cnum)*travel.getTravel_insurance());
		} else {
			total_fee = (int) ((trtr_mnum*mprice+trtr_cnum*cprice)*discount+(trtr_mnum+trtr_cnum)*travel.getTravel_insurance());
		}
		
		TravelTrade travelTrade = new TravelTrade();;
		travelTrade.setTravel(travel);
		travelTrade.setTrtr_cnum(trtr_cnum);
		travelTrade.setTrtr_mnum(trtr_mnum);
		travelTrade.setTrtr_price(total_fee);
		travelTrade.setTrtr_tel(trtr_tel);
		travelTrade.setTrtr_num(out_trade_no);
		travelTrade.setOpen_id(openid);
		travelTrade.setTrade_discount(travel.getTravel_discount());
		travelTrade.setIs_state(0);
		//TravelTrade trTrade=travelService.saveTravelTrade(travelTrade);
		
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("attach", attach);
		paraMap.put("total_fee", String.valueOf(total_fee));
		paraMap.put("body", wxPayConstants.TRAVELBODY);
		Map<String, String> mapResult = jspay(paraMap, request, responest);
		
		json.put("appId",  mapResult.get("appid"));
		json.put("timeStamp", mapResult.get("timeStamp"));
		json.put("nonceStr", mapResult.get("nonceStr"));
		json.put("pg", mapResult.get("prepay_id"));
		json.put("signType", "MD5");
		json.put("paySign", mapResult.get("paySign"));
		json.put("total_fee",total_fee);
		json.put("out_trade_no",mapResult.get("out_trade_no"));
		System.out.println(json.toString());
		return json.toString();
	}

	
	@RequestMapping("/travelRefundPay.do")
	public @ResponseBody String travelRefundPay(HttpServletRequest request, HttpServletResponse responest) throws Exception{
		String trtr_id = request.getParameter("travel_trade_id");
		TravelTrade list = travelService.selectTrTrInfoById(trtr_id);
		String refund_fee = StringUtil.save0Float(list.getTrtr_price()*0.8);
   	 	// 获取系统当前时间. 存入数据库
        Date now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String data = dateFormat.format(now);
		//对比时间，设定开团前24小时内不能退票
		Date sTime = list.getTravel().getTravel_stime();
		long interval = sTime.getTime() - now.getTime();
		// 24时(h)=86400000毫秒(ms)
		JSONObject json = new JSONObject();
		if(interval < 86400000){
			String e = "退票时间不符合要求";
			json.put("e", e);
			return json.toString();
		}
		
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("out_trade_no", list.getTrtr_num());
		paraMap.put("total_fee", list.getTrtr_price().toString());
		paraMap.put("refund_fee", refund_fee);
		
		String result = jsRefundPay(paraMap, request, responest);
        Map<String, String> mapResult = wxPayUtil.xmlToMap(result);
        if(mapResult.get("result_code").equals("SUCCESS")) {
        	int mnun = list.getTrtr_mnum();
    		int cnum = list.getTrtr_cnum();
    		Travel travel = list.getTravel();
    		int travel_left_num = travel.getTravel_left_num();
    		int left_num = travel_left_num + mnun + cnum;
    		//更新剩余票数
        	travelService.updateRefundTravel(left_num,travel.getTravel_id());
        	//将退票信息存入交易信息
            if(mapResult.get("refund_id") != null){
            	travelService.updateRefundTrade(mapResult.get("refund_id"), refund_fee, data, trtr_id);
            } else {
            	travelService.updateRefundTrade(refund_fee, data, trtr_id);
            }
        }else if(mapResult.get("result_code").equals("FAIL")) {
			json.put("e", "退款失败，失败原因：" + mapResult.get("err_code_des"));
			return json.toString();
        }
		
		return list.toString();
	}
	@RequestMapping("/requestPay.do")
	public @ResponseBody Map<String, String> jspay(Map<String, String> para, HttpServletRequest request, HttpServletResponse responest) throws Exception{
		
		String openid = SessionUtil.getOpenid(request);
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
		paraMap.put("body", "洛川-旅游"); //*body字段
		paraMap.put("attach", para.get("attach")); //附加数据
		paraMap.put("openid", openid); //*用户信息
		paraMap.put("out_trade_no", out_trade_no); //*商户订单号
		paraMap.put("fee_type", "CNY");//标价币种
		paraMap.put("total_fee", String.valueOf(para.get("total_fee"))); //*订单总金额，单位为分（需前台传来）
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
		Map<String, String> mapResult = new HashMap<>();
		mapResult.put("appId", (String) map.get("appid"));
		mapResult.put("timeStamp", timeStamp2);
		mapResult.put("nonceStr", nonceStr2);
		mapResult.put("pg", prepay_id);
		mapResult.put("signType", wxPayConstants.SIGNTYPE);
		mapResult.put("paySign", paySign);
		mapResult.put("out_trade_no",out_trade_no);
		mapResult.put("total_fee",map.get("total_fee"));
		System.out.println(mapResult.toString());
		return mapResult;
	}
	
	public @ResponseBody String jsRefundPay(Map<String, String> map, HttpServletRequest request, HttpServletResponse responest) throws Exception{
		Map<String, String> paraMap = new HashMap<String, String>();
		String jsonStr = "";
		
		paraMap.put("appid", wxPayConstants.APPID);//* 
		paraMap.put("mch_id", wxPayConstants.MCH_ID);//* 
		paraMap.put("out_trade_no", (String) map.get("out_trade_no"));
		paraMap.put("total_fee",(String) map.get("total_fee"));
		paraMap.put("refund_fee",(String) map.get("refund_fee"));
		paraMap.put("nonce_str", wxPayUtil.create_nonce_str());
		paraMap.put("out_refund_no", (String) map.get("out_trade_no"));
		String sign = wxPayUtil.generateSignature(paraMap, wxPayConstants.PATERNERKEY);
		paraMap.put("sign", sign); //*签名
		
		// 退款接口链接 https://api.mch.weixin.qq.com/secapi/pay/refund
		String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		//参数值用XML转义即可，CDATA标签用于说明数据不被XML解析器解析.detail不被XML解析
		String xml = wxPayUtil.mapToXml(paraMap);

		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(wxPayConstants.FILEPATH));//放退款证书的路径
        try {
            keyStore.load(instream, wxPayConstants.MCH_ID.toCharArray());
        } finally {
            instream.close();
        }
        
        // 证书
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, wxPayConstants.MCH_ID.toCharArray()).build();
        // 只允许TLSv1协议
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[]{"TLSv1"},null,
        		SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        //创建基于证书的httpClient,后面要用到
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        HttpPost httpPost = new HttpPost(url);//退款接口
        StringEntity reqEntity = new StringEntity(xml);
        // 设置类型
        reqEntity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(reqEntity);
        CloseableHttpResponse response = client.execute(httpPost);
        try {
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine());
            jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("---------------------华丽丽的分割线开始---------------------");
            System.out.println(jsonStr);
            System.out.println("---------------------华丽丽的分割线结束---------------------");

//            if (entity != null) {
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
//                String text = "";
//                while ((text = bufferedReader.readLine()) != null) {
//                    sb.append(text);
//                }
//            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
		return jsonStr;
	}
		
}
