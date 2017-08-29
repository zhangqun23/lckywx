package com.mvc.controller;

import javax.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mvc.entiy.User;
import com.mvc.repository.UserRepository;
import com.utils.OpenidUtil;

/**
 * 路由跳转相关
 * 
 * @author zq
 * @date 2017年8月9日
 */
@Controller
@RequestMapping("/routeController")
public class RouteController extends HttpServlet {
	@Autowired
	UserRepository userRepository;

	@RequestMapping("/toPlatformPage.do")
	public String InvoiceReceivePage() {
//		String str0 = getInitParameter("appid");
//		String code = getInitParameter("response_type");
//		System.out.println("慧敏君来了" + str0);
//		OpenidUtil.getOpenid(null, null, code);
//		User user = new User();
//		user.setUser_openId(OpenidUtil.getOpenid(null, null, code));
//		User result = userRepository.saveAndFlush(user);

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

	@RequestMapping("/toSelectAdListPage.do")
	public String toSelectAdListPage() {
		return "adver/index";
	}

	@RequestMapping("/toAddAdListPage.do")
	public String toAddAdListPage() {
		return "adver/index";
	}

}
