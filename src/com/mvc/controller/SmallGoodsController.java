package com.mvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entity.SmallGoods;
import com.mvc.service.SmallGoodsService;
import com.utils.Pager;
import com.utils.SessionUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 小件货运
 * 
 * @author lijing
 * @date 2017年8月11日
 */
@Controller
@RequestMapping("/smallGoods")
public class SmallGoodsController {
	@Autowired
	SmallGoodsService smallGoodsService;

	/**
	 * 添加和修改小件货运信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addSmallGoods.do")
	public @ResponseBody String addSmallGoods(HttpServletRequest request, HttpSession session, HttpServletResponse res)
			throws ParseException {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("goNeed"));
		SmallGoods smallGoods = new SmallGoods();
		String openid = SessionUtil.getOpenid(request);
		smallGoods.setOpenid(openid);
		if (jsonObject.containsKey("smgo_name")) {
			smallGoods.setSmgo_name(jsonObject.getString("smgo_name"));
		}
		if (jsonObject.containsKey("smgo_weight")) {
			smallGoods.setSmgo_weight(Float.parseFloat(jsonObject.getString("smgo_weight")));
		}
		if (jsonObject.containsKey("smgo_start")) {
			smallGoods.setSmgo_start(jsonObject.getString("smgo_start"));
		}
		if (jsonObject.containsKey("smgo_end")) {
			smallGoods.setSmgo_end(jsonObject.getString("smgo_end"));
		}
		if (jsonObject.containsKey("smgo_sender")) {
			smallGoods.setSmgo_sender(jsonObject.getString("smgo_sender"));
		}
		if (jsonObject.containsKey("smgo_add")) {
			smallGoods.setSmgo_add(jsonObject.getString("smgo_add"));
		}
		if (jsonObject.containsKey("smgo_sender_tel")) {
			smallGoods.setSmgo_sender_tel(jsonObject.getString("smgo_sender_tel"));
		}
		if (jsonObject.containsKey("smgo_receiver")) {
			smallGoods.setSmgo_receiver(jsonObject.getString("smgo_receiver"));
		}
		if (jsonObject.containsKey("smgo_receiver_tel")) {
			smallGoods.setSmgo_receiver_tel(jsonObject.getString("smgo_receiver_tel"));
		}
		if (jsonObject.containsKey("amgo_money")) {
			smallGoods.setAmgo_money(Float.parseFloat(jsonObject.getString("amgo_money")));
		} else {
			smallGoods.setAmgo_money((float) 0.123);
		}

		if (jsonObject.containsKey("smgo_send_time")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(jsonObject.getString("smgo_send_time"));
			smallGoods.setSmgo_send_time(date);
		}
		if (jsonObject.containsKey("smgo_remark")) {
			smallGoods.setSmgo_remark(jsonObject.getString("smgo_remark"));
		}
		smallGoods.setIs_delete(false);
		smallGoods.setIsFinish(false);
		SmallGoods result;
		if (jsonObject.containsKey("smgo_id")) {
			smallGoods.setSmgo_id(Integer.valueOf(jsonObject.getString("smgo_id")));
			result = smallGoodsService.saveSmallGoods(smallGoods);// 修改班车定制需求
		} else {
			Date date = new Date();
			smallGoods.setSmgo_deal_time(date);
			result = smallGoodsService.saveSmallGoods(smallGoods);// 添加班车定制需求
		}

		JSONObject limit = new JSONObject();
		limit.put("result", result);
		return limit.toString();
	}

	/**
	 * 查询小件货运信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/selectSmallGoods.do")
	public @ResponseBody String selectSmallGoods(HttpServletRequest request, HttpSession session)
			throws ParseException {
		String openId = SessionUtil.getOpenid(request);
		String page = request.getParameter("page");
		String state = request.getParameter("state");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openId", openId);
		map.put("state", state);
		Pager pager = new Pager();
		if (!page.equals("") && !page.equals(null)) {
			pager.setPage(Integer.parseInt(page));
		} else {
			pager.setPage(1);
		}
		map.put("offset", pager.getOffset());
		map.put("limit", pager.getLimit());
		List<SmallGoods> list = smallGoodsService.findSmallGoodsAlls(map);

		JSONObject jsonO = new JSONObject();
		jsonO.put("list", list);
		return jsonO.toString();
	}

	/**
	 * 查询小件货运信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/selectSmallGoodsInfo.do")
	public @ResponseBody String selectSmallGoodsInfo(HttpServletRequest request, HttpSession session)
			throws ParseException {
		SmallGoods list = null;
		String smgo_id = request.getParameter("smgo_id");
		if (smgo_id != null) {
			list = smallGoodsService.findSmallGoodsById(smgo_id);
		}
		JSONObject jsonO = new JSONObject();
		jsonO.put("list", list);
		return jsonO.toString();
	}

}
