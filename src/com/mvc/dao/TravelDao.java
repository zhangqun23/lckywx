/**
 * @Title: TravelDao.java
 * @Package com.mvc.dao
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:56:15 
 */
package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;

/**
 * @ClassName: TravelDao
 * @Description: 旅游信息查询
 * @author ycj
 * @date 2017年8月14日 上午11:56:15 
 */
public interface TravelDao {
	

	List<Travel> findTravelAlls1(Map<String, Object> map);//按成人票价格查询旅游信息
	
	List<TravelTrade> saveTravelTrade(TravelTrade travelTrade);//旅游交易信息
	
	List<Travel> findTravelAlls();//旅游查询
	
	List<Travel> findTravelAlls(Map<String, Object> map);//按出发时间查询旅游信息
}
