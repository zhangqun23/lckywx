package com.mvc.dao;

import java.util.List;

import com.mvc.entity.TravelTrade;

public interface TravelTradeDao {

	List<TravelTrade> selectMyTravelInfoByOId(String openid, Integer offset, Integer limit, String state);

	void addTravelTrade(TravelTrade travelTrade);

	void saveTradeState(String trade_num, String transaction_id, String datanow);

	List<Object> getTravelTrade(String out_trade_no);

	void updateTravelTrade(TravelTrade travelTrade);

}
