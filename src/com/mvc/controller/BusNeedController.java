package com.mvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.base.enums.IsDelete;
import com.mvc.entiy.BusNeed;
import com.mvc.entiy.BusTrade;
import com.mvc.entiy.User;
import com.mvc.service.BusNeedService;
import com.utils.SessionUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 班车
 * 
 * @author wanghuimin
 * @date 2017年8月9日
 */
@Controller
@RequestMapping("/busNeed")
public class BusNeedController {
	@Autowired
	BusNeedService busNeedService;

	/**
	 * 添加,修改班车定制需求
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
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_gath_pla"))) {
				busNeed.setBune_gath_pla(jsonObject.getString("bune_gath_pla"));
			}
		}
		if (jsonObject.containsKey("bune_gath_time")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_gath_time"))) {
				Date date = sdf.parse(jsonObject.getString("bune_gath_time"));
				busNeed.setBune_gath_time(date);
			}
		}
		if (jsonObject.containsKey("bune_goal_pla")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_goal_pla"))) {
				busNeed.setBune_goal_pla(jsonObject.getString("bune_goal_pla"));
			}
		}
		if (jsonObject.containsKey("bune_num")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_num"))) {
				busNeed.setBune_num(Integer.parseInt(jsonObject.getString("bune_num")));
			}
		}
		if (jsonObject.containsKey("bune_purp")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_purp"))) {
				busNeed.setBune_purp(jsonObject.getString("bune_purp"));
			}
		}
		if (jsonObject.containsKey("bune_remark")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_remark"))) {
				busNeed.setBune_remark(jsonObject.getString("bune_remark"));
			}
		}
		if (jsonObject.containsKey("bune_tel")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_tel"))) {
				busNeed.setBune_tel(jsonObject.getString("bune_tel"));
			}
		}
		if (jsonObject.containsKey("bune_time")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_time"))) {
				busNeed.setBune_time(Float.parseFloat(jsonObject.getString("bune_time")));
			}
		}
		String openid=SessionUtil.getOpenid(request);
		busNeed.setOpen_id(openid);
		busNeed.setIs_delete(true);
		BusNeed result = null;
		BusTrade result1=null;
		if (jsonObject.containsKey("bune_id")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_id"))) {
				busNeed.setBune_id(Integer.valueOf(jsonObject.getString("bune_id")));
			}
			result = busNeedService.saveBusNeed(busNeed);// 修改班车定制需求
		} else {
			result = busNeedService.saveBusNeed(busNeed);// 添加班车定制需求
		}
		if(result!=null){	
			BusNeed busAndNeed = new BusNeed();
			busAndNeed.setBune_id(result.getBune_id());
			result1.setBusNeed(busAndNeed);
			result1 = busNeedService.saveAndBusTrade(result1);// 修改的同时添加班车交易
		}		
		JSONObject limit=new JSONObject();
		limit.put("result", result);
		limit.put("result1", result1);
		return limit.toString();
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
		//String openid=SessionUtil.getOpenid(request);
		String openid="1";
		Map<String, Object> map = new HashMap<String, Object>();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");	
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("openid", openid);
		List<BusNeed> list = busNeedService.findBusNeedAlls(map);
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
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_bus"))) {
				busTrade.setBune_bus(jsonObject.getString("bune_bus"));
			}
		}
		if (jsonObject.containsKey("bune_type")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_type"))) {
				busTrade.setBune_type(Integer.valueOf(jsonObject.getString("bune_type")));
			}
		}
		if (jsonObject.containsKey("butr_depo")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("butr_depo"))) {
				busTrade.setButr_depo(Float.parseFloat(jsonObject.getString("butr_depo")));
			}
		}
		if (jsonObject.containsKey("butr_money")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("butr_money"))) {
				busTrade.setButr_money(Float.parseFloat(jsonObject.getString("butr_money")));
			}
		}
		if (jsonObject.containsKey("butr_state")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("butr_state"))) {
				busTrade.setButr_state(Integer.valueOf(jsonObject.getString("butr_state")));
			}
		}
		if (jsonObject.containsKey("butr_time")) {
			SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
			if (StringUtil.strIsNotEmpty(jsonObject.getString("butr_time"))) {
				Date date = sdf.parse(jsonObject.getString("butr_time"));
				busTrade.setButr_time(date);
			}
		}
		if (jsonObject.containsKey("invoice_if")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("invoice_if"))) {
				busTrade.setInvoice_if(Integer.valueOf(jsonObject.getString("invoice_if")));
			}
		}
		if (jsonObject.containsKey("invoice_num")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("invoice_num"))) {
				busTrade.setInvoice_num(jsonObject.getString("invoice_num"));
			}
		}
		BusNeed busNeed = new BusNeed();
		if (jsonObject.containsKey("bus_need")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bus_need"))) {
				busNeed.setBune_id(Integer.parseInt(jsonObject.getJSONObject("bus_need").getString("bune_id")));
				busTrade.setBusNeed(busNeed);
			}
		}
		boolean result = false;
		if (jsonObject.containsKey("butr_id")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("butr_id"))) {
				busTrade.setButr_id(Integer.valueOf(jsonObject.getString("butr_id")));
				result = busNeedService.saveBusTrade(busTrade);// 修改班车交易
			}
		} else {
			result = busNeedService.saveBusTrade(busTrade);// 添加班车交易
		}
		
		JSONObject limit=new JSONObject();
		limit.put("result", result);
		return limit.toString();
	}
	
	/**
	 * 删除班车定制需求
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/deleteBusNeed.do")
	public @ResponseBody String deleteBusNeed(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject = new JSONObject();
		String openid=SessionUtil.getOpenid(request);
		Map<String, Object> map = new HashMap<String, Object>();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Integer busNeed_id = Integer.valueOf(request.getParameter("busNeed_id"));	
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("busNeed_id", busNeed_id);
		map.put("openid", openid);
		boolean out = busNeedService.deleteBusNeed(map);
		List<BusNeed> list = busNeedService.findBusNeedAlls(map);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	/**
	 * 查看单个班车预定需求
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/selectBusNeedById.do")
	public @ResponseBody String selectBusTrades(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer busNeed_id = Integer.valueOf(request.getParameter("busNeed_id"));		
		map.put("busNeed_id", busNeed_id);	
		BusNeed busNeed = busNeedService.findBusNeedAll(map);
		BusTrade busTrade = busNeedService.findBusTradeAll(map);
		jsonObject.put("busNeed", busNeed);
		jsonObject.put("busTrade", busTrade);
		return jsonObject.toString();
	}
	
	
}
