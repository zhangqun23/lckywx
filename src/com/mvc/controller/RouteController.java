package com.mvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mvc.repository.UserRepository;
//import com.utils.OpenidUtil;

/**
 * 路由跳转相关
 * 
 * @author zq
 * @date 2017年8月9日
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/routeController")
public class RouteController extends HttpServlet {
	@Autowired
	UserRepository userRepository;

	@RequestMapping("/toPlatformPage.do")
	public String InvoiceReceivePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException   {
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//
//		String code = getInitParameter("code");
//		String openid = OpenidUtil.getOpenid(null, null, code);
		String openid = "wang123";
		HttpSession session = getSession();
		session.setAttribute("openid", openid);

		return "platform/index";
	}

	  public static HttpSession getSession() { 
          HttpSession session = null; 
           try { 
                session = getRequest().getSession(); 
            } catch (Exception e) {} 
              return session; 
       } 
	  
	  public static HttpServletRequest getRequest() { 
          ServletRequestAttributes attrs =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); 
          return attrs.getRequest(); 
    }

	@RequestMapping("/toBusNeedPage.do")
	public String toBusNeedPage() {
		return "busNeed/index";
	}

	@RequestMapping("/toTravelInfoPage.do")
	public String totravellInfoPage() {
		return "travelInfo/index";
	}

	@RequestMapping("/toTravelInfoDetailPage.do")
	public String totravelInfoDetailPage() {
		return "travelInfo/index";
	}

	@RequestMapping("/toSmallGoodsPage.do")
	public String toSmallGoodsPage() {
		return "smallGoods/index";
	}

	@RequestMapping("/toSmallGoodsListPage.do")
	public String toSmallGoodsListPage() {
		return "smallGoods/index";
	}

	@RequestMapping("/toSelectAdListPage.do")
	public String toSelectAdListPage() {
		return "adver/index";
	}

	@RequestMapping("/toAddAdListPage.do")
	public String toAddAdListPage() {
		return "adver/index";
	}

	
	@RequestMapping("/toMyTradePage.do")
	public String toMyTradePage() {
		return "myTrade/index";
	}
	
	


}
