package com.mvc.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mvc.entiy.Driver;
import com.mvc.entiy.Truck;
import com.mvc.entiy.Truck_need;
import com.mvc.entiy.Truck_send;
import com.mvc.service.TruckDriverService;
import com.utils.SessionUtil;
import com.utils.StringUtil;
import net.sf.json.JSONObject;

/**
 * 零担货运
 * @author ghl
 * @date   2017年9月6日
 */
@Controller
@RequestMapping("/truckLoad")
public class TruckDriverController {
	@Autowired
	TruckDriverService truckDriverService;
	/**
	 * 添加司机信息
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addTruckDriver.do")
	public @ResponseBody String addTruckDriver(HttpServletRequest request) throws ParseException{
		JSONObject jsonObject2 = JSONObject.fromObject(request.getParameter("truckInfo"));
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("driverInfo"));
		Truck truck = new Truck();
		Driver driver = new Driver();
		if(jsonObject.containsKey("driver_name")){
			if(StringUtil.strIsNotEmpty(jsonObject.getString("driver_name"))){
				driver.setDriver_name(jsonObject.getString("driver_name"));
			}
		}
		if(jsonObject.containsKey("driver_job")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_job"))) {
				driver.setDriver_job(jsonObject.getString("driver_job"));
			}
		}
		if (jsonObject.containsKey("driver_tel")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_tel"))) {
				driver.setDriver_tel(jsonObject.getString("driver_tel"));
			}	
		}
		if (jsonObject.containsKey("driver_idcard")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_idcard"))) {
				driver.setDriver_idcard(jsonObject.getString("driver_idcard"));
			}
		}
		if (jsonObject.containsKey("driver_license_starttime")) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_license_starttime"))) {
				Date date = format.parse(jsonObject.getString("driver_license_starttime"));
				driver.setDriver_license_starttime(date);
			}
		}	
		if (jsonObject.containsKey("driver_license")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_license"))) {
				driver.setDriver_license(jsonObject.getString("driver_license"));
			}
		}
		if (jsonObject.containsKey("driver_image")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_image"))) {
				driver.setDriver_image(jsonObject.getString("driver_image"));
			}
		}
		if (jsonObject.containsKey("driver_car")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_car"))) {
				driver.setDriver_car(jsonObject.getString("driver_car"));
			}
		}
		driver.setIs_audit(0);
		if (jsonObject2.containsKey("trck_load")) {
			if (StringUtil.strIsNotEmpty(jsonObject2.getString("trck_load"))) {
				truck.setTrck_load(Float.parseFloat(jsonObject2.getString("trck_load")));
			}
		}
		if (jsonObject2.containsKey("is_freeze")) {
			if (StringUtil.strIsNotEmpty(jsonObject2.getString("is_freeze"))) {
				truck.setIs_freeze(Integer.parseInt(jsonObject2.getString("is_freeze")));
			}
		}
		truck.setTrck_check(0);
		Driver result = truckDriverService.addDriver(driver);
		truck.setDriver(result);
		Truck limint = truckDriverService.addTruck(truck);
		JSONObject jsonO = new JSONObject();
		jsonO.put("result", result);
		jsonO.put("limint", limint);
		return jsonO.toString();
	}
	/**
	 * 添加货车主录入信息
	 * @param request
	 * return list
	 * @throws ParseException 
	 */
	@RequestMapping("/addTruckSend.do")
	public @ResponseBody String addTruckSend (HttpServletRequest request) throws ParseException{
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("trneInfo"));
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
		Truck_send result = truckDriverService.addTruckSend(truckSend);
		JSONObject jsonO = new JSONObject();
		jsonO.put("result", result);
		return jsonO.toString();
	}
	/**
	 * 添加货主基本需求信息
	 * @param request
	 * return list
	 * @throws ParseException 
	 */
	@RequestMapping("/addTruckNeed.do")
	public @ResponseBody String addTruckNeed (HttpServletRequest request ) throws ParseException{
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("trneInfo"));
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
		Truck_need result = truckDriverService.addTruckNeed(truckNeed);
		JSONObject jsonO = new JSONObject();
		jsonO.put("result", result);
		return jsonO.toString();
	}
	
	/**
	 * 货主查询车辆根据目的地，出发时间
	 * @param request
	 * return list
	 */
	@RequestMapping("/aa.do")
	public @ResponseBody String aa (HttpServletRequest request){
		String trse_eplace = request.getParameter("g"); 
		String startTime  = request.getParameter("h");
		String endTime = request.getParameter("l");
		Map<String, Object>map = new HashMap<String,Object>();
		map.put("trse_eplace", trse_eplace);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<Truck_send> list = truckDriverService.findTruckSend(map);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	/**
	 * 车主查询货源根据始发地、目的地，出发时间
	 */
	@RequestMapping("/bb.do")
	public @ResponseBody String bb (HttpServletRequest request){
		String trne_eplace = request.getParameter("g"); 
		String startTime  = request.getParameter("h");
		String endTime = request.getParameter("l");
		Map<String, Object>map = new HashMap<String,Object>();
		map.put("trne_eplace", trne_eplace);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<Truck_need> list = truckDriverService.findTruckNeed(map);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
}
