package com.mvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mvc.entiy.BusNeed;
import com.mvc.entiy.BusTrade;
import com.mvc.entiy.User;
import com.mvc.service.BusNeedService;

import net.sf.json.JSONObject;

/**
 * 班车
 * 
 * @author wanghuimin
 * @date 2017年8月9日
 */
@Controller("/busNeed")
public class BusNeedController {
	@Autowired
	BusNeedService busNeedService;

	/**
	 * 添加班车定制需求
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addBusNeed.do")
	public @ResponseBody String addBusNeed(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("busNeed"));
		BusNeed busNeed = new BusNeed();
		if (jsonObject.containsKey("bune_gath_pla")) {
			busNeed.setBune_gath_pla(jsonObject.getString("bune_gath_pla"));
		}
		if (jsonObject.containsKey("bune_gath_time")) {
			SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
			Date date = sdf.parse(jsonObject.getString("bune_gath_time"));
			busNeed.setBune_gath_time(date);
		}
		if (jsonObject.containsKey("bune_goal_pla")) {
			busNeed.setBune_goal_pla(jsonObject.getString("bune_goal_pla"));
		}
		if (jsonObject.containsKey("bune_num")) {
			busNeed.setBune_num(Integer.parseInt(jsonObject.getString("bune_num")));
		}
		if (jsonObject.containsKey("bune_purp")) {
			busNeed.setBune_purp(jsonObject.getString("bune_purp"));
		}
		if (jsonObject.containsKey("bune_remark")) {
			busNeed.setBune_remark(jsonObject.getString("bune_remark"));
		}
		if (jsonObject.containsKey("bune_tel")) {
			busNeed.setBune_tel(jsonObject.getString("bune_tel"));
		}
		if (jsonObject.containsKey("bune_time")) {
			busNeed.setBune_time(Float.parseFloat(jsonObject.getString("bune_time")));
		}
		if (jsonObject.containsKey("is_delete")) {
			busNeed.setIs_delete(Boolean.parseBoolean(jsonObject.getString("is_delete")));
		}
		User user = new User();
		user.setUser_id(Integer.parseInt(jsonObject.getJSONObject("user").getString("user_id")));
		busNeed.setUser(user);
		boolean result;
		if (jsonObject.containsKey("bune_id")) {
			busNeed.setBune_id(Integer.valueOf(jsonObject.getString("bune_id")));
			result = busNeedService.saveBusNeed(busNeed);// 修改班车定制需求
		} else {
			result = busNeedService.saveBusNeed(busNeed);// 添加班车定制需求
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 查询班车定制需求
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/selectBusNeed.do")
	public @ResponseBody String selectBusNeed(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String useDate = request.getParameter("useDate");
		List<BusNeed> list = busNeedService.findBusNeedAlls(useDate);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

	/**
	 * 添加,修改班车交易
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addBusTrade.do")
	public @ResponseBody String addBusTrade(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("busTrade"));
		BusTrade busTrade = new BusTrade();
		if (jsonObject.containsKey("bune_bus")) {
			busTrade.setBune_bus(jsonObject.getString("bune_bus"));
		}
		if (jsonObject.containsKey("bune_type")) {
			busTrade.setBune_type(Integer.valueOf(jsonObject.getString("bune_type")));
		}

		if (jsonObject.containsKey("butr_depo")) {
			busTrade.setButr_depo(Float.parseFloat(jsonObject.getString("butr_depo")));
		}
		if (jsonObject.containsKey("butr_money")) {
			busTrade.setButr_money(Float.parseFloat(jsonObject.getString("butr_money")));
		}
		if (jsonObject.containsKey("butr_state")) {
			busTrade.setButr_state(Integer.valueOf(jsonObject.getString("butr_state")));
		}
		if (jsonObject.containsKey("butr_time")) {
			SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
			Date date = sdf.parse(jsonObject.getString("butr_time"));
			busTrade.setButr_time(date);
		}
		if (jsonObject.containsKey("invoice_if")) {
			busTrade.setInvoice_if(Integer.valueOf(jsonObject.getString("invoice_if")));
		}
		if (jsonObject.containsKey("invoice_num")) {
			busTrade.setInvoice_num(jsonObject.getString("invoice_num"));
		}
		BusNeed busNeed = new BusNeed();
		busNeed.setBune_id(Integer.parseInt(jsonObject.getJSONObject("bus_need").getString("bune_id")));
		busTrade.setBusNeed(busNeed);
		boolean result;
		if (jsonObject.containsKey("butr_id")) {
			busTrade.setButr_id(Integer.valueOf(jsonObject.getString("butr_id")));
			result = busNeedService.saveBusTrade(busTrade);// 修改用户信息
		} else {
			result = busNeedService.saveBusTrade(busTrade);// 添加用户信息
		}
		return JSON.toJSONString(result);

	}
}
