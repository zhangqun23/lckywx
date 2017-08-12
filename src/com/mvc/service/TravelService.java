package com.mvc.service;

import java.util.List;

import com.mvc.entiy.Travel;
/**
 * 
 * @ClassName: TravelService
 * @Description: 查询旅游信息
 * @author ycj
 * @date 2017年8月12日 上午9:44:59 
 * 
 *
 */
public interface TravelService {
	
		List<Travel> findTravelAlls(String useDate);// 查询旅游信息

}
