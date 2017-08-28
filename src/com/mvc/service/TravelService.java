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
	List<Travel> findTravelAlls0(Map<String, Object> map);//直接查询
	List<Travel> findTravelAlls(Map<String, Object> map);//按出发时间查询旅游信息
	List<Travel> findTravelAlls1(Map<String, Object> map);//按成人票价格查询旅游信息
	List<TravelTrade> saveTravelTrade(TravelTrade travelTrade);//添加旅游交易
}
