/**
 * @Title: TravelService.java
 * @Package com.mvc.service
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:49:16 
 */
package com.mvc.service;

import java.util.List;
import java.util.Map;

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
	
	List<TravelTrade> saveTravelTrade(TravelTrade travelTrade);//旅游交易
	
	//根据id查找travel
	Travel findTravelById(String travelid);
}
