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

import com.mvc.entiy.BusNeed;
import com.mvc.service.BusNeedService;
import com.utils.Pager;
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
		String openid = SessionUtil.getOpenid(request);
		busNeed.setOpen_id(openid);
		busNeed.setIs_delete(true);
		busNeed.setBune_type(1);
		busNeed.setButr_depo((float) 0.0);
		busNeed.setButr_money((float) 0.0);
		busNeed.setButr_state(0);
		busNeed.setInvoice_if(0);
		Date date=new Date(); 
		busNeed.setBune_insert_time(date);
		BusNeed result = null;
		if (jsonObject.containsKey("bune_id")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("bune_id"))) {
				busNeed.setBune_id(Integer.valueOf(jsonObject.getString("bune_id")));
			}
			result = busNeedService.saveBusNeed(busNeed);// 修改班车定制需求
		} else {
			result = busNeedService.saveBusNeed(busNeed);// 添加班车定制需求
		}
		JSONObject limit = new JSONObject();
		limit.put("result", result);
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
		// String openid=SessionUtil.getOpenid(request);
		String openid = "wang123";
		Map<String, Object> map = new HashMap<String, Object>();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String page = request.getParameter("page");
		String state = request.getParameter("state");
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("openid", openid);
		map.put("page", page);
		map.put("state", state);
		Pager pager = new Pager();
		pager.setPage(Integer.parseInt(page));
		map.put("offset", pager.getOffset());
		map.put("limit", pager.getLimit());
		List<BusNeed> list = busNeedService.findBusNeedAlls(map);
		jsonObject.put("list", list);
		return jsonObject.toString();
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
		String openid = SessionUtil.getOpenid(request);
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
		jsonObject.put("busNeed", busNeed);
		return jsonObject.toString();
	}

}
