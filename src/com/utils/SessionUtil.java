package com.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	
	public static String getOpenid(HttpServletRequest request){
		HttpSession session = request.getSession();
		String openid = (String) session.getAttribute("openid");
//		String openid = "wang123";
		return openid;
		
	}
	
	//未用，考虑会不会丢失openID
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
		FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		HttpSession session=request.getSession();
		if((String) session.getAttribute("openid")==null){
			String errors = "未获取到您的基本信息。请重新进入页面!";
			request.setAttribute("Message", errors);
			request.getRequestDispatcher("/AdminLogin.jsp").forward(request, response);
		}else{
			arg2.doFilter(request, response);
			}
		}

	public void init(FilterConfig arg0) throws ServletException {
		//初始化操作，读取web.xml中过滤器配置的初始化参数，满足你提的要求不用此方法
	}

}
