package com.mvc.controller;

import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entity.Ad;
import com.mvc.repository.AdRepository;
import com.mvc.service.AdService;
import com.utils.Pager;
import com.utils.SessionUtil;
import com.utils.StringUtil;
import net.sf.json.JSONObject;

/**
 * 
 * 广告
 * 
 * @author ghl
 * @date   2017年8月15日
 */
@Controller
@RequestMapping("/ad")
public class AdController {
	@Autowired
	AdService  adService;
	/**
	 * 添加，修改广告
	 * 
	 * @param request
	 * @param session
	 * @return  ad对象
	 * @throws ParseException
	 */
	@RequestMapping("/addAd.do")
	public @ResponseBody String addAd(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject= JSONObject.fromObject(request.getParameter("ad"));
		Ad ad= new  Ad();
		if (jsonObject.containsKey("ad_type")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_type"))){
			ad.setAd_type(Integer.parseInt(jsonObject.getString("ad_type")));
			}
		}
		if (jsonObject.containsKey("ad_name")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_name"))){
				ad.setAd_name(jsonObject.getString("ad_name"));
			}
		}
		if (jsonObject.containsKey("ad_tel")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_tel"))){
				ad.setAd_tel(jsonObject.getString("ad_tel"));
			}	
		}
		if (jsonObject.containsKey("ad_title")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_title"))){
				ad.setAd_title(jsonObject.getString("ad_title"));
			}
		}
		if (jsonObject.containsKey("ad_etime")){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_etime"))){
				Date date = format.parse(jsonObject.getString("ad_etime"));
				ad.setAd_etime(date);
			}
		}
	/*	if (jsonObject.containsKey("ad_pic_path")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_pic_path"))){
				ad.setAd_pic_path(jsonObject.getString("ad_pic_path"));
			}
		}*/
		if (jsonObject.containsKey("ad_remark")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_remark"))){
				ad.setAd_remark(jsonObject.getString("ad_remark"));
			}else{
				ad.setAd_remark("");
			}
		}
		if (jsonObject.containsKey("ad_content")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_content"))){
				ad.setAd_content(jsonObject.getString("ad_content"));
			}
		}
		Date getDate = new Date();
		ad.setAd_stime(getDate);
		ad.setIs_delete(false);	
		ad.setAd_state(0);
		String openid = SessionUtil.getOpenid(request);
		ad.setOpen_id(openid);
		Ad result = adService.saveAd(ad);	
		JSONObject limit = new JSONObject();
		limit.put("result", result);
		return limit.toString(); 
	}
	/**
	 * 添加，修改广告
	 * 
	 * @param request
	 * @param session
	 * @return  ad对象
	 * @throws ParseException
	 */
	@RequestMapping("/modifyAd.do")
	public @ResponseBody String modifyAd(HttpServletRequest request, HttpSession session) {
		Integer adId = Integer.parseInt(request.getParameter("ad_id"));
		JSONObject jsonObject= JSONObject.fromObject(request.getParameter("ad"));
		Ad result = adService.saveAdRpeat(jsonObject,adId);
		JSONObject limit = new JSONObject();
		limit.put("result", result);
		return limit.toString();	
	}
	

	/**
	 * 广告查询
	 * 根据类型查询 若为空则返回全部类型广告，否则返回相应类型广告
	 * @param request
	 * @param session
	 * @return  list
	 */
	@RequestMapping("/selectAdver.do")
	public @ResponseBody String selectAdver(HttpServletRequest request, HttpSession session){
		String adType;	
		Pager pager = new Pager();
		pager.setPage(Integer.valueOf(request.getParameter("page")));
		adType= request.getParameter("adType");
		List<Ad> list = adService.finAdByType(Integer.parseInt(adType),pager.getOffset(), pager.getLimit());		
		JSONObject jsonObject = new JSONObject();
		if(list.size() != 0){
			jsonObject.put("list", list);
		}else{
			jsonObject.put("list", null);
		}
		return jsonObject.toString();
	}
	/**
	 * 广告删除根据广告id
	 * @param request
	 * @return true false
	 * 
	 */
	@RequestMapping("/deleteAd.do")
	public @ResponseBody String deleteAd (HttpServletRequest request){
		System.out.println("nihao");
		Integer ad_id = Integer.parseInt(request.getParameter("adId"));
		Boolean flag = adService.deleteAd(ad_id);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", flag);
		return jsonObject.toString();
	}
	
	/**
	 * 根据id查找广告list
	 * @param request
	 * @return list
	 * 
	 * */
	@RequestMapping("/selectAdverInfo.do")
	public @ResponseBody String selectAdverInfo (HttpServletRequest request){
		Ad list;
		String adId = request.getParameter("ad_id");
		list = adService.selectAdverInfo(adId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
		
	}
	/**
	 * 根据openId查找广告
	 * @param request
	 * @return list
	 */
	@RequestMapping("/myPlace.do")
	public @ResponseBody String myPlace (HttpServletRequest request, HttpSession session){
		String openId = SessionUtil.getOpenid(request);
		List<Ad> list = new ArrayList<Ad>();
		String adState = request.getParameter("ad_state");
		Pager pager = new Pager();
		pager.setPage(Integer.valueOf(request.getParameter("page")));
		list = adService.findMyPlaceAd(Integer.parseInt(adState),openId,pager.getOffset(), pager.getLimit());
		
		JSONObject jsonO = new JSONObject();
		if(list.size() != 0){
			jsonO.put("list", list);
		}else{
			jsonO.put("list", null);
		}		
		return jsonO.toString();
	}
	
}
