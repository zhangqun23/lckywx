package com.mvc.controller;

import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mvc.entiy.Ad;
import com.mvc.entiy.User;
import com.mvc.service.AdService;
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
	 * @return
	 * @throws ParseException
	 * 
	 * 
	 */
	@RequestMapping("/addAd.do")
	public @ResponseBody String addAd(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject= JSONObject.fromObject(request.getParameter("ad"));
		Ad ad= new  Ad();
		if (jsonObject.containsKey("ad_type")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_type"))){
			ad.setAd_type(Integer.parseInt(jsonObject.getString("ad_type")));
			}
		}
		if (jsonObject.containsKey("ad_state")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_state"))){
				ad.setAd_state(Integer.parseInt(jsonObject.getString("ad_state")));
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
		if (jsonObject.containsKey("ad_pic_path")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_pic_path"))){
				ad.setAd_pic_path(jsonObject.getString("ad_pic_path"));
			}
		}
		if (jsonObject.containsKey("ad_remark")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_remark"))){
				ad.setAd_remark(jsonObject.getString("ad_remark"));
			}
		}
		if (jsonObject.containsKey("ad_content")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_content"))){
				ad.setAd_content(jsonObject.getString("ad_content"));
			}
		}
		User user= new User();
		user.setUser_id(Integer.parseInt(jsonObject.getJSONObject("user").getString("user_id")));
		ad.setUser(user);
		Ad result = null;
		if (jsonObject.containsKey("ad_id")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_id"))){
				ad.setAd_id(Integer.valueOf(jsonObject.getString("ad_id")));
				result = adService.saveAd(ad);// 修改广告	
			}
		} else {
			result = adService.saveAd(ad);// 添加广告
		}
		JSONObject limit = new JSONObject();
		limit.put("result", result);
		return limit.toString(); 
	}

}
