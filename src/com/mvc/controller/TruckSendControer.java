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

import com.mvc.entiy.Truck;
import com.mvc.entiy.Truck_send;
import com.mvc.service.TruckSendService;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 车主录入信息
 * @author ghl
 * @date   2017年9月6日
 */
@Controller
@RequestMapping("")
public class TruckSendControer {
	@Autowired
	TruckSendService truckSendService;
	/**
	 * 添加货车主录入信息
	 * @param request
	 * return list
	 * @throws ParseException 
	 */
	@RequestMapping("/addTruckSend.do")
	public @ResponseBody String addTruckSend (HttpServletRequest request) throws ParseException{
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter(""));
		Truck_send truckSend = new Truck_send();
		if (jsonObject.containsKey("trse_left_load")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trse_left_load"))) {
				truckSend.setTrse_left_load(Float.parseFloat(jsonObject.getString("trse_left_load")));
			}
		}
		if (jsonObject.containsKey("trse_splace")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trse_splace"))) {
				truckSend.setTrse_splace(jsonObject.getString("trse_splace"));
			}
		}
		if (jsonObject.containsKey("trse_eplace")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trse_eplace"))) {
				truckSend.setTrse_eplace(jsonObject.getString("trse_eplace"));
			}
		}
		if (jsonObject.containsKey("trse_price")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trse_price"))) {
				truckSend.setTrse_price(jsonObject.getString("trse_price"));
			}
		}
		if (jsonObject.containsKey("trse_time")) {
			DateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trse_time"))) {
				Date date = (Date) formt.parse(jsonObject.getString("trse_time"));
				truckSend.setTrse_time(date);
			}
		}
		Truck truck = new Truck();
		truck.setTrck_id(Integer.parseInt(request.getParameter("trck_id")));
		truckSend.setTruck(truck);
		Truck_send result = truckSendService.addTruckSend(truckSend);
		JSONObject jsonO = new JSONObject();
		jsonO.put("result", result);
		return jsonO.toString();
	}

}
