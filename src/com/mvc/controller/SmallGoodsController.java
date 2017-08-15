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
import com.mvc.entiy.SmallGoods;
import com.mvc.service.SmallGoodsService;

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
	public @ResponseBody String addBusNeed(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("smallGoods"));
		SmallGoods smallGoods = new SmallGoods();
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
		}
		if (jsonObject.containsKey("smgo_deal_time")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(jsonObject.getString("smgo_deal_time"));
			smallGoods.setSmgo_deal_time(date);
		}
		if (jsonObject.containsKey("smgo_send_time")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(jsonObject.getString("smgo_send_time"));
			smallGoods.setSmgo_send_time(date);
		}
		if (jsonObject.containsKey("smgo_remark")) {
			smallGoods.setSmgo_remark(jsonObject.getString("smgo_remark"));
		}		
		smallGoods.setIs_delete(true);
		
		boolean result;
		if (jsonObject.containsKey("smgo_id")) {
			smallGoods.setSmgo_id(Integer.valueOf(jsonObject.getString("smgo_id")));
			result = smallGoodsService.saveSmallGoods(smallGoods);// 修改班车定制需求
		} else {
			result = smallGoodsService.saveSmallGoods(smallGoods);// 添加班车定制需求
		}
		return JSON.toJSONString(result);
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
	public @ResponseBody String selectBusNeed(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject = new JSONObject();
		String endPlace = request.getParameter("endPlace");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sendTime = sdf.parse(request.getParameter("sendTime"));
		List<SmallGoods> list = smallGoodsService.findSmallGoodsAlls(endPlace ,sendTime);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

}
