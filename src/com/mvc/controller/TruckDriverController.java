package com.mvc.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mvc.entiy.Driver;
import com.mvc.entiy.Truck;
import com.mvc.service.TruckDriverService;
import com.utils.StringUtil;
import net.sf.json.JSONObject;

/**
 * 零担货运
 * @author ghl
 * @date   2017年9月6日
 */
@Controller
@RequestMapping("/truckDriver")
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
		JSONObject jsonObject2 = JSONObject.fromObject(request.getParameter("truck"));
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("driver"));
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
	
	//根据driver_id查询driver
	
	
	

}
