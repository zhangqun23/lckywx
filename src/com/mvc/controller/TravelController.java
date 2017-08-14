package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entiy.Travel;
import com.mvc.service.TravelService;

import net.sf.json.JSONObject;
/**
 * 
 * @ClassName: TravelController
 * @Description: 旅游列表查询
 * @author ycj
 * @date 2017年8月12日 上午9:42:27 
 * 
 *
 */
@Controller
@RequestMapping("/travel")
public class TravelController {
	@Autowired
	TravelService travelService;
	/**
	 * 
	 * 
	 *@Title: selectBusNeed 
	 *@Description: 查询旅游信息   list列表
	 *@param @param request
	 *@param @param session
	 *@param @return
	 *@return String
	 *@throws
	 */
	@RequestMapping(value="/travelInfo.do")
	public @ResponseBody String selectBusNeed(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String useDate = request.getParameter("useDate");
		String usePrice = request.getParameter("usePrice");
		List<Travel> list = travelService.findTravelAlls(useDate);
		List<Travel> list1 = travelService.findTravelAlls1(usePrice);
		
		jsonObject.put("list", list);
		jsonObject.put("list1", list1);
		return jsonObject.toString();
	}

}
