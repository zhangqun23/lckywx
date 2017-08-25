package com.mvc.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.constants.ReportFormConstants;
import com.mvc.entityReport.CheckHouse;
import com.mvc.entiy.BusNeed;
import com.mvc.entiy.User;
import com.mvc.repository.BusNeedRepository;
import com.mvc.repository.UserRepository;
import com.mvc.service.CheckHouseService;
import com.utils.CollectionUtil;
import com.utils.CookieUtil;
import com.utils.OpenidUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 路由跳转相关
 * 
 * @author zq
 * @date 2017年8月9日
 */
@Controller
@RequestMapping("/routeController")
public class RouteController extends HttpServlet{
	@Autowired
	UserRepository userRepository;


	@RequestMapping("/toPlatformPage.do")
	public String InvoiceReceivePage() {
		String str0=getInitParameter("appid");
		String code=getInitParameter("response_type");
		System.out.println("慧敏君来了"+str0);
		OpenidUtil.getOpenid(null, null, code);
		User user=new User();
		user.setUser_openId(OpenidUtil.getOpenid(null, null, code));
		User result = userRepository.saveAndFlush(user);
		
		return "platform/index"; 
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
	
	

}
