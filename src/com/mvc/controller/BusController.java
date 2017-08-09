package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entity.User;
import com.utils.Pager;

import net.sf.json.JSONObject;

/**
 * 班车
 * @author wanghuimin
 * @date 2017年8月9日
 */
@Controller("/bus")
public class BusController {
	
	/**
	 * 添加班车定制需求
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addBusNeed.do")
	public @ResponseBody String getUsersByPrarm(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String searchKey = request.getParameter("searchKey");
		Integer totalRow = userService.countTotal(searchKey);
		Pager pager = new Pager();
		pager.setPage(Integer.valueOf(request.getParameter("page")));
		pager.setTotalRow(Integer.parseInt(totalRow.toString()));
		List<User> list = userService.findUserAllByPage(searchKey, pager.getOffset(), pager.getLimit());
		jsonObject.put("list", list);
		jsonObject.put("totalPage", pager.getTotalPage());
		return jsonObject.toString();
	}

}
