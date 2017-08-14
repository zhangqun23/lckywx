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

@Controller
@RequestMapping("/travel")
public class TravelController {
	@Autowired
	TravelService travelService;
	
	@RequestMapping(value = "/travelInfo.do")
	public @ResponseBody String selectTravel(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String useDate = request.getParameter("useDate");
		List<Travel> list = travelService.findTravelAlls(useDate);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

}
