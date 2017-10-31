package com.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.utils.OpenidUtil;

public class SessionFilter implements Filter {

	public void destroy() {
		// 过滤器销毁，一般是释放资源
	}

	/**
	 * 某些url需要登陆才能访问（session验证过滤器）
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		HttpSession session=request.getSession();
		
		if((String) session.getAttribute("openid")==null){
			String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();  
			if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器  
				System.out.println("是微信浏览器");
				OpenidUtil.GetopenidUrl(response);
			} else {
				String errors = "未获取到您的基本信息。请重新进入页面!";
				request.setAttribute("Message", errors);
				request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
			}
		} else {
			arg2.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// 初始化操作，读取web.xml中过滤器配置的初始化参数，满足你提的要求不用此方法
	}
}
