/**
 * @Title: TravelService.java
 * @Package com.mvc.service
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:49:16 
 */
package com.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mvc.entity.Travel;
import com.mvc.entity.TravelTrade;

/**
 * @ClassName: TravelService
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:49:16 
 */
public interface TravelService {
	List<Travel> findTravelAlls(Integer offset, Integer limit);//直接查询
	
	//TravelTrade saveTravelTrade(TravelTrade travelTrade);//旅游交易
	//void saveTravelTrade(TravelTrade travelTrade);//旅游交易
	void updateTravelTrade(TravelTrade travelTrade);
	
	//根据id查找travel
	Travel findTravelById(String travelid);

	//更新剩余票数
	void updateTravel(String travel_id, Integer total_num);

	//根据openid查找旅游信息
	List<TravelTrade> selectMyTravelInfoByOId(String openid, Integer offset, Integer limit, String state);

	//根据trtr_id查找旅游交易信息
	TravelTrade selectTrTrInfoById(String trtr_id);

	//根据travel_id更新剩余票数
	void updateRefundTravel(int left_num, Integer travel_id);

	//根据trtr_id更新退款信息
	void updateRefundTrade(String refund_id, String refund_fee, String data ,String trtr_id);

	void updateRefundTrade(String refund_fee, String data, String trtr_id);

	void saveTravelTrade(TravelTrade travelTrade);
}
