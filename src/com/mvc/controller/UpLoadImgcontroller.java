package com.mvc.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mvc.constants.wxPayConstants;
import com.mvc.entity.AccessToken;
import com.mvc.service.AccesstokenService;
import com.utils.HttpKit;
import com.utils.wxPayUtil;


@Controller
@RequestMapping("/upLoadImg")
public class UpLoadImgcontroller {
	
	@Autowired
	AccesstokenService accesstokenService;

	@RequestMapping("/getImgCon.do")
	public @ResponseBody String getImgCon(HttpServletRequest request, HttpServletResponse responest) throws Exception{
		
		HttpServletRequest httpRequest=(HttpServletRequest)request;  	  
		String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + httpRequest.getContextPath() + httpRequest.getServletPath();
		System.out.println("当前页面地址为"+strBackUrl);
		JSONObject json = new JSONObject();
		String timestamp = wxPayUtil.getCurrentTimestamp();
		String nonce = wxPayUtil.create_nonce_str();
		String jsapi_ticket = getJsApiTicket(timestamp);

		//String string = "jsapi_ticket="+jsapi_ticket+"&noncestr="+nonce+"&timestamp="+timestamp+"&url="+strBackUrl;
		String string = "jsapi_ticket="+jsapi_ticket+"&noncestr="+nonce+"&timestamp="+timestamp+"&url="+"http://lckywx.cc/lckywx/jsp/wximgUpLoad.jsp";
		System.out.println(string);
		String signature = wxPayUtil.SHA1(string);
		
		json.put("appID", wxPayConstants.APPID);
		json.put("timestamp", timestamp);
		json.put("nonce", nonce);
		json.put("signature", signature);
		return json.toString();
		
	}
	
	public @ResponseBody String getJsApiTicket(String timestamp) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, 
				ExecutionException, InterruptedException{
		
		List<AccessToken> accesstoken = accesstokenService.getAccesstoken();
		String jsapi_ticket = "";
		
		if(accesstoken.size()>0){
			long timestamps =  Long.parseLong(timestamp) - accesstoken.get(0).getActo_time();
			if(timestamps>7200){
		        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?";
		        String params = "grant_type=client_credential&appid=" + wxPayConstants.APPID + "&secret=" + wxPayConstants.APPSECRET + "";
		    	String result = HttpKit.get(requestUrl+params);
		        String access_token = JSONObject.parseObject(result).getString("access_token");
		        requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";
		        params = "access_token=" + access_token + "&type=jsapi";
		        result = HttpKit.get(requestUrl+params);  
		        jsapi_ticket = JSONObject.parseObject(result).getString("ticket");
		        accesstokenService.updateAccesstoken(jsapi_ticket, Long.parseLong(timestamp),accesstoken.get(0).getActo_id());
			} else {
				jsapi_ticket = accesstoken.get(0).getActo_num();
			}
		}
		return jsapi_ticket;
	}

}
