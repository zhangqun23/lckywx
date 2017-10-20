package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entity.Travel;
import com.mvc.entity.TravelTrade;
import com.mvc.service.TravelService;
import com.utils.Pager;
import com.utils.SessionUtil;
import com.utils.StringUtil;

import net.sf.json.JSONObject;
/**
 * @ClassName: TravelController
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 下午2:52:39 
 */
@Controller
@RequestMapping("/travelInfo")
public class TravelController {
	@Autowired
	TravelService travelService;
	/**
	 * 
	 * 
	 *@Title: selectTravel 
	 *@Description: 旅游查询
	 *@param @param request
	 *@param @param session
	 *@param @return
	 *@return String
	 *@throws
	 */
	@RequestMapping(value = "/selectTravelInfo.do")   //select Travel 
	public @ResponseBody String selectTravelAll(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		Pager pager = new Pager();
		pager.setPage(Integer.valueOf(request.getParameter("page")));
		List<Travel> list = travelService.findTravelAlls(pager.getOffset(), pager.getLimit());
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	
	@RequestMapping("/selectTravelInfoById.do")
	public @ResponseBody String selectTravelInfoById(HttpServletRequest request, HttpSession session) {
		String Travel_id = request.getParameter("travel_id_select");
		JSONObject jsonObject = new JSONObject();
		Travel list = travelService.findTravelById(Travel_id);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	
	//交易完成后存入交易信息
	@RequestMapping("/saveTravelTrade.do")
	public @ResponseBody String saveTravelTrade(HttpServletRequest request, HttpSession session){
		String total_fee = request.getParameter("total_fee");
		String out_trade_no = request.getParameter("out_trade_no");
		String travel_id = request.getParameter("travelidbuy");
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("tradeNeed"));
		TravelTrade travelTrade = new TravelTrade();
		if (jsonObject.containsKey("trtr_tel")) {
			if(StringUtil.strIsNotEmpty(jsonObject.getString("trtr_tel"))){
				travelTrade.setTrtr_tel(jsonObject.getString("trtr_tel"));
			}	
		}
		if (jsonObject.containsKey("trtr_mnum")) {
			if(StringUtil.strIsNotEmpty(jsonObject.getString("trtr_mnum"))){
				travelTrade.setTrtr_mnum(Integer.valueOf(jsonObject.getString("trtr_mnum")));
			}
		}
		if (jsonObject.containsKey("trtr_cnum")) {
			if(StringUtil.strIsNotEmpty(jsonObject.getString("trtr_cnum"))){
				travelTrade.setTrtr_cnum(Integer.valueOf(jsonObject.getString("trtr_cnum")));
			}
		}
		//更新剩余人数
//		Integer total_num = travelTrade.getTrtr_mnum() + travelTrade.getTrtr_cnum();
//		travelService.updateTravel(travel_id,total_num);
		
		travelTrade.setTrtr_price(Integer.parseInt(total_fee));
		travelTrade.setTrtr_num(out_trade_no);
		travelTrade.setOpen_id(SessionUtil.getOpenid(request));
		travelTrade.setIs_state(0);
		Travel travel = new Travel();
		travel.setTravel_id(Integer.parseInt(travel_id));
		travelTrade.setTravel(travel);
		TravelTrade temp = travelService.saveTravelTrade(travelTrade);
		
		return temp.toString();
	}
	
	//根据OPENID查找旅游信息
	@RequestMapping("/selectMyTravelInfoByOId.do")
	public @ResponseBody String selectMyTravelInfoByOId(HttpServletRequest request, HttpSession session){
		JSONObject jsonObject = new JSONObject();
		Pager pager = new Pager();
		pager.setPage(Integer.valueOf(request.getParameter("page")));
		String openid = SessionUtil.getOpenid(request);
		String state = request.getParameter("state");
		List<TravelTrade> list = travelService.selectMyTravelInfoByOId(openid,pager.getOffset(), pager.getLimit(), state);
		if(list.size() != 0){
			jsonObject.put("list", list);
		}else{
			jsonObject.put("list", null);
		}
		return jsonObject.toString();
	}
}
