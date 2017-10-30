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

import com.mvc.entity.Driver;
import com.mvc.entity.Truck;
import com.mvc.entity.TruckNeed;
import com.mvc.entity.TruckSend;
import com.mvc.service.TruckDriverService;
import com.utils.Pager;
import com.utils.SessionUtil;
import com.utils.StringUtil;
import net.sf.json.JSONObject;

/**
 * 零担货运
 * 
 * @author ghl
 * @date 2017年9月6日
 */
@Controller
@RequestMapping("/truckLoad")
public class TruckDriverController {
	@Autowired
	TruckDriverService truckDriverService;

	/**
	 * 添加司机信息
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/addTruckDriver.do")
	public @ResponseBody String addTruckDriver(HttpServletRequest request) throws ParseException {
		JSONObject jsonObject2 = JSONObject.fromObject(request.getParameter("truckInfo"));
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("driverInfo"));
		Truck truck = new Truck();
		Driver driver = new Driver();
		String openId = SessionUtil.getOpenid(request);
		driver.setOpen_id(openId);
		truck.setOpen_id(openId);
		if (jsonObject.containsKey("driver_name")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_name"))) {
				driver.setDriver_name(jsonObject.getString("driver_name"));
			}
		}
		if (jsonObject.containsKey("driver_job")) {
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
		if (jsonObject.containsKey("driver_license_number")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_license_number"))) {
				driver.setDriver_license_number(jsonObject.getString("driver_license_number"));
			}
		}
		if (jsonObject.containsKey("is_audit")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("is_audit"))) {
				driver.setIs_audit(Integer.parseInt(jsonObject.getString("is_audit")));
			}
		} else {
			driver.setIs_audit(0);
		}
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
		if (jsonObject2.containsKey("trck_number")) {
			if (StringUtil.strIsNotEmpty(jsonObject2.getString("trck_number"))) {
				truck.setTrck_number((jsonObject2.getString("trck_number")));
			}
		}
		Driver result = null;
		if (jsonObject.containsKey("driver_id")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("driver_id"))) {
				driver.setDriver_id(Integer.valueOf(jsonObject.getString("driver_id")));
			}
			result = truckDriverService.addDriver(driver);// 修改班车定制需求
		} else {
			result = truckDriverService.addDriver(driver);// 添加班车定制需求
		}
		truck.setDriver(result);
		if (jsonObject2.containsKey("trck_score")) {
			if (StringUtil.strIsNotEmpty(jsonObject2.getString("trck_score"))) {
				truck.setTrck_score(jsonObject2.getString("trck_score"));
			}
		} else {
			truck.setTrck_score("0");
		}
		if (jsonObject2.containsKey("trck_num")) {
			if (StringUtil.strIsNotEmpty(jsonObject2.getString("trck_num"))) {
				truck.setTrck_num(Integer.parseInt(jsonObject2.getString("trck_num")));
			}
		} else {
			truck.setTrck_num(1);
		}
		if (jsonObject2.containsKey("is_delete")) {
			if (StringUtil.strIsNotEmpty(jsonObject2.getString("is_delete"))) {
				truck.setIs_delete(Boolean.getBoolean(jsonObject2.getString("is_delete")));
			}
		} else {
			truck.setIs_delete(false);
		}
		truck.setTrck_check(0);
		Truck limint = null;
		if (jsonObject2.containsKey("trck_id")) {
			if (StringUtil.strIsNotEmpty(jsonObject2.getString("trck_id"))) {
				truck.setTrck_id(Integer.valueOf(jsonObject2.getString("trck_id")));
			}
			limint = truckDriverService.addTruck(truck);// 修改班车定制需求
		} else {
			limint = truckDriverService.addTruck(truck);// 添加班车定制需求
		}
		JSONObject jsonO = new JSONObject();
		jsonO.put("result", result);
		jsonO.put("limint", limint);
		return jsonO.toString();
	}

	/**
	 * 添加货车主录入信息
	 * 
	 * @param request
	 *            return list
	 * @throws ParseException
	 */
	@RequestMapping("/addTruckSend.do")
	public @ResponseBody String addTruckSend(HttpServletRequest request) throws ParseException {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("trseInfo"));
		JSONObject jsonO = new JSONObject();
		TruckSend truckSend = new TruckSend();
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
		String openId = SessionUtil.getOpenid(request);
		Truck truck = truckDriverService.findTruck(openId);
		if (truck == null) {
			jsonO.put("result", null);
			return jsonO.toString();
		} else {
			truckSend.setTruck(truck);
		}

		TruckSend result = null;
		if (jsonObject.containsKey("trse_id")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trse_id"))) {
				truckSend.setTrse_id(Integer.valueOf(jsonObject.getString("trse_id")));
			}
			result = truckDriverService.addTruckSend(truckSend);// 修改班车定制需求
		} else {
			result = truckDriverService.addTruckSend(truckSend);// 添加班车定制需求
		}

		jsonO.put("result", result);
		return jsonO.toString();
	}

	/**
	 * 添加货主基本需求信息
	 * 
	 * @param request
	 *            return list
	 * @throws ParseException
	 */
	@RequestMapping("/addTruckNeed.do")
	public @ResponseBody String addTruckNeed(HttpServletRequest request) throws ParseException {

		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("trneInfo"));
		TruckNeed truckNeed = new TruckNeed();
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
		if (jsonObject.containsKey("trne_check")) {
			if (StringUtil.strIsNotEmpty("trne_check")) {
				truckNeed.setTrne_check(Integer.parseInt(jsonObject.getString("trne_check")));
			}
		} else {
			truckNeed.setTrne_check(0);
		}

		if (jsonObject.containsKey("trne_remark")) {
			if (StringUtil.strIsNotEmpty("trne_remark")) {
				truckNeed.setTrne_remark(jsonObject.getString("trne_remark"));
			} else {
				return null;
			}
		}
		if (jsonObject.containsKey("is_freeze")) {
			if (StringUtil.strIsNotEmpty("is_freeze")) {
				truckNeed.setIs_freeze(Integer.parseInt(jsonObject.getString("is_freeze")));
			}
		}
		if (jsonObject.containsKey("trne_receive_name")) {
			if (StringUtil.strIsNotEmpty("trne_receive_name")) {
				truckNeed.setTrne_receive_name(jsonObject.getString("trne_receive_name"));
			}
		}
		if (jsonObject.containsKey("trne_receive_tel")) {
			if (StringUtil.strIsNotEmpty("trne_receive_tel")) {
				truckNeed.setTrne_receive_tel(jsonObject.getString("trne_receive_tel"));
			}
		}
		String openId = SessionUtil.getOpenid(request);
		truckNeed.setOpen_id(openId);
		Date date = new Date();
		truckNeed.setTrne_insert_time(date);
		TruckNeed result = null;
		if (jsonObject.containsKey("trne_id")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("trne_id"))) {
				truckNeed.setTrne_id(Integer.valueOf(jsonObject.getString("trne_id")));
			}
			result = truckDriverService.addTruckNeed(truckNeed);// 修改班车定制需求
		} else {
			result = truckDriverService.addTruckNeed(truckNeed);// 添加班车定制需求
		}
		JSONObject jsonO = new JSONObject();
		jsonO.put("result", result);
		return jsonO.toString();
	}

	/**
	 * 货主查询车辆根据目的地，出发时间
	 * 
	 * @param request
	 *            return list
	 */
	@RequestMapping("/selectTruckSend.do")
	public @ResponseBody String selectTruckSend(HttpServletRequest request) {
		String openId = SessionUtil.getOpenid(request);
		String page = request.getParameter("page");
		Truck truck = truckDriverService.findTruck(openId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("truckId", truck.getTrck_id());
		Pager pager = new Pager();
		if (!page.equals("") && !page.equals(null)) {
			pager.setPage(Integer.parseInt(page));
		} else {
			pager.setPage(1);
		}
		map.put("offset", pager.getOffset());
		map.put("limit", pager.getLimit());
		List<TruckSend> list = truckDriverService.findTruckSendList(map);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

	/**
	 * 车主查询货源根据始发地、目的地，出发时间
	 */
	@RequestMapping("/selectTruckNeed.do")
	public @ResponseBody String selectTruckNeed(HttpServletRequest request) {
		String openId = SessionUtil.getOpenid(request);
		String page = request.getParameter("page");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		Pager pager = new Pager();
		if (!page.equals("") && !page.equals(null)) {
			pager.setPage(Integer.parseInt(page));
		} else {
			pager.setPage(1);
		}
		map.put("offset", pager.getOffset());
		map.put("limit", pager.getLimit());
		List<TruckNeed> list = truckDriverService.findTruckNeed(map);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

	/**
	 * 根据truckSendId查询truckSend详情
	 */
	@RequestMapping("/selectTruckSendById.do")
	public @ResponseBody String selectTruckSendById(HttpServletRequest request) {
		Integer trseId = Integer.parseInt(request.getParameter("trse_id"));
		TruckSend truckSend = ((TruckDriverService) truckDriverService).findTruckSendInfo(trseId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("truckSend", truckSend);
		return jsonObject.toString();
	}

	/**
	 * 根据truckNeedId查询truckNeed详情
	 */
	@RequestMapping("/selectTruckNeedById.do")
	public @ResponseBody String selectTruckNeedById(HttpServletRequest request) {
		Integer trneId = Integer.parseInt(request.getParameter("trne_id"));
		TruckNeed truckNeed = truckDriverService.findTruckNeedInfo(trneId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("truckNeed", truckNeed);
		return jsonObject.toString();
	}

	/**
	 * 根据trck_id和driver_id查询truckDriver信息
	 */
	@RequestMapping("/selectTruckDriverById.do")
	public @ResponseBody String selectTruckDriverById(HttpServletRequest request) {
		Integer trckId = Integer.parseInt(request.getParameter("trckId"));
		Integer driverId = Integer.parseInt(request.getParameter("driverId"));
		Truck truck = truckDriverService.findTrck(trckId);
		Driver driver = truckDriverService.findDriver(driverId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("truck", truck);
		jsonObject.put("driver", driver);
		return jsonObject.toString();
	}

	/**
	 * zq查询用户的所有登记的货车
	 */
	@RequestMapping("/selectUserTruck.do")
	public @ResponseBody String selectUserTruck(HttpServletRequest request) {
		String openid = SessionUtil.getOpenid(request);
		List<Truck> list = null;
		Driver driver = truckDriverService.selectDriverByOpenId(openid);
		if (driver != null) {
			list = truckDriverService.selectUserTruck(driver.getDriver_id());
		}
		JSONObject jsonObject = new JSONObject();
		int flag = 3;// 3表示未添加过0表示待审核1表示已审核可以发布需求2表示审核未通过
		if (list == null) {
			flag = 3;
		} else {
			Truck truck = list.get(0);
			if (truck.getTrck_check() == 0) {
				flag = 0;// 待审核
			} else if (truck.getTrck_check() == 1) {
				flag = 1;// 审核未通过
			} else if (truck.getTrck_check() == 2) {
				flag = 2;// 审核未通过
			}

		}
		jsonObject.put("list", list);
		jsonObject.put("flag", flag);
		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}

	/**
	 * zq查询用户的所有登记的货车
	 */
	@RequestMapping("/selectNewTruckSend.do")
	public @ResponseBody String selectNewTruckSend(HttpServletRequest request) {

		String page = request.getParameter("page");
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = new Pager();
		if (!page.equals("") && !page.equals(null)) {
			pager.setPage(Integer.parseInt(page));
		} else {
			pager.setPage(1);
		}
		map.put("offset", pager.getOffset());
		map.put("limit", pager.getLimit());
		List<TruckSend> list = truckDriverService.findNewTruckSendList(map);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

	/**
	 * 车主查询货源根据始发地、目的地，出发时间
	 */
	@RequestMapping("/selectNewTruckNeed.do")
	public @ResponseBody String selectNewTruckNeed(HttpServletRequest request) {
		String page = request.getParameter("page");
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = new Pager();
		if (!page.equals("") && !page.equals(null)) {
			pager.setPage(Integer.parseInt(page));
		} else {
			pager.setPage(1);
		}
		map.put("offset", pager.getOffset());
		map.put("limit", pager.getLimit());
		List<TruckNeed> list = truckDriverService.findNewTruckNeed(map);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
}
