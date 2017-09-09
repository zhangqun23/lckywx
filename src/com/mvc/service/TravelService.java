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
 */
public interface TravelService {
	List<Travel> findTravelAlls();//直接查询
	
	TravelTrade saveTravelTrade(TravelTrade travelTrade);//旅游交易
	
	//根据id查找travel
	Travel findTravelById(String travelid);

	//更新剩余票数
	void updateTravel(String travel_id, Integer total_num);
}
