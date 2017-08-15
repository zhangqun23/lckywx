/**
 * @Title: TravelService.java
 * @Package com.mvc.service
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:49:16 
 */
package com.mvc.service;

import java.util.List;

import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;

/**
 * @ClassName: TravelService
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:49:16 
 * 
 *
 */
public interface TravelService {

	//按出发时间查询旅游信息
	List<Travel> findTravelAlls(String useDate);
	//按成人票价格查询旅游信息
	List<Travel> findTravelAlls1(String usePrice);
	//添加旅游交易
	boolean saveTravelTrade(TravelTrade travelTrade);
	
	
}
