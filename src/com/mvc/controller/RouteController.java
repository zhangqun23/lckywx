package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * 路由跳转相关
 * 
 * @author zq
 * @date 2017年8月9日
 */
@Controller
@RequestMapping("/routeController")
public class RouteController {



	@RequestMapping("/toPlatformPage.do")
	public String InvoiceReceivePage() {
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
