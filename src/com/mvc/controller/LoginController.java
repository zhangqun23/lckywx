package com.mvc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.mvc.service.LoginService;

import net.sf.json.JSONObject;



@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	//token
	private final String token = "tongchema";
	
	@RequestMapping("/toLoginPage.do")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		  
		ArrayList<String> array = new ArrayList<String>();
		array.add(signature);
		array.add(timestamp);
		array.add(nonce);
		
		String sortString = "";
		
		if(nonce == null || timestamp == null){
			nonce = "123";
			timestamp = "456";
		}
		//排序
		sortString = sort1(token, timestamp, nonce);

		 //加密
		String mytoken = SHA1(sortString);

		/** 读取接收到的xml消息 */
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();//得到请求消息的数据输入流
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");//以UTF-8的编码方式，一次读取一个一个字符，以文本格式输入输出
		BufferedReader br = new BufferedReader(isr);//读取文本
		String s = "";
		//用于request内容的解析
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		String xml = sb.toString();	//次即为接收到微信端发送过来的xml数据

		String result = "";
		/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
		String echostr = request.getParameter("echostr");
		if (echostr != null && echostr.length() > 1 && mytoken != null && mytoken != "" && mytoken.equals(signature)) {
			result = echostr;
		} else {
			//正常的微信处理流程
			result = loginService.wechatProcess(xml);
		}

		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 排序方法
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String sort1(String token, String timestamp, String nonce) {
	 String[] strArray = { token, timestamp, nonce };
	 Arrays.sort(strArray);
	  
	 StringBuilder sbuilder = new StringBuilder();
	 for (String str : strArray) {
	 sbuilder.append(str);
	 }
	  
	 return sbuilder.toString();
	}
	
	/**
	 * 加密方法
	 * @para decript
	 * @return
	 * 
	 * */
	 public static String SHA1(String decript) {
	 try {
		 MessageDigest digest = MessageDigest.getInstance("SHA-1");
		 digest.update(decript.getBytes());
		 byte messageDigest[] = digest.digest();
		 // Create Hex String
		 StringBuffer hexString = new StringBuffer();
		 // 字节数组转换为 十六进制 数
		 for (int i = 0; i < messageDigest.length; i++) {
			 String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
			 if (shaHex.length() < 2) {
				 hexString.append(0);
			 }
			 hexString.append(shaHex);
		 }
		 return hexString.toString();
	  
	 	} catch (NoSuchAlgorithmException e) {
	 		e.printStackTrace();
	 	}
	 	return "";
	 }

}
