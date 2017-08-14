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
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 下午2:52:39 
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
	 *@Title: selectTravelByDate 
	 *@Description: 按出发时间查询
	 *@param @param request
	 *@param @param session
	 *@param @return
	 *@return String
	 *@throws
	 */
	@RequestMapping(value = "/travelInfo.do")
	public @ResponseBody String selectTravelByDate(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String useDate = request.getParameter("useDate");
		List<Travel> list = travelService.findTravelAlls(useDate);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	/**
	 * 
	 * 
	 *@Title: selectTravelByPrice 
	 *@Description: 按成人票价查询
	 *@param @param request
	 *@param @param session
	 *@param @return
	 *@return String
	 *@throws
	 */
	public @ResponseBody String selectTravelByPrice(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String usePrice = request.getParameter("usePrice");
		List<Travel> list = travelService.findTravelAlls1(usePrice);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
}
