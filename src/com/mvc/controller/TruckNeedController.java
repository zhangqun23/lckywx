package com.mvc.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entiy.Truck_need;
import com.mvc.service.TruckNeedService;
import com.utils.SessionUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 货主信息录入
 * @author ghl
 * @date   2017年9月6日
 */
@Controller
@RequestMapping("/truckNeed")
public class TruckNeedController {
	@Autowired
	TruckNeedService truckNeedService;
	/**
	 * 添加货主基本需求信息
	 * @param request
	 * return list
	 * @throws ParseException 
	 */
	@RequestMapping("/addTruckNeed.do")
	public @ResponseBody String addTruckNeed (HttpServletRequest request ) throws ParseException{
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("truckNeed"));
		Truck_need truckNeed = new Truck_need();
		if (jsonObject.containsKey("trne_name")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trne_name"))) {
				truckNeed.setTrne_name(jsonObject.getString("trne_name"));
			}
		}
		if (jsonObject.containsKey("trne_tel")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trne_tel"))) {
				truckNeed.setTrne_tel(jsonObject.getString("trne_tel"));
			}
		}
		if (jsonObject.containsKey("trne_type")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trne_type"))) {
				truckNeed.setTrne_type(jsonObject.getString("trne_type"));
			}
		}
		if (jsonObject.containsKey("trne_weight")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trne_weight"))) {
				truckNeed.setTrne_weight(Float.parseFloat(jsonObject.getString("trne_weight")));
			}
		}
		if (jsonObject.containsKey("trne_splace")) {
			if (StringUtil.strIsNotEmpty("trne_splace")) {
				truckNeed.setTrne_splace(jsonObject.getString("trne_splace"));
			}
		}
		if (jsonObject.containsKey("trne_eplace")) {
			if (StringUtil.strIsNotEmpty("trne_eplace")) {
				truckNeed.setTrne_eplace(jsonObject.getString("trne_eplace"));
			}
		}
		if (jsonObject.containsKey("trne_time")) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtil.strIsNotEmpty("trne_time")) {
				Date date = (Date) format.parse(jsonObject.getString("trne_time"));
				truckNeed.setTrne_time(date);
			}	
		}
		truckNeed.setTrne_check(0);
		if (jsonObject.containsKey("trne_remark")) {
			if (StringUtil.strIsNotEmpty("trne_remark")) {
				truckNeed.setTrne_remark(jsonObject.getString("trne_remark"));
			}
		}
		if (jsonObject.containsKey("is_freeze")) {
			if (StringUtil.strIsNotEmpty("is_freeze")) {
				truckNeed.setIs_freeze(Integer.parseInt(jsonObject.getString("is_freeze")));
			}
		}
		String openId = SessionUtil.getOpenid(request);
		truckNeed.setOpen_id(openId);
		Truck_need result = truckNeedService.addTruckNeed(truckNeed);
		JSONObject jsonO = new JSONObject();
		jsonO.put("result", result);
		return jsonO.toString();
	}
}
