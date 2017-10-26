package com.mvc.dao;

import java.util.List;

import com.mvc.entity.TravelTrade;

public interface TravelTradeDao {

	List<TravelTrade> selectMyTravelInfoByOId(String openid, Integer offset, Integer limit, String state);

	void addTravelTrade(TravelTrade travelTrade);

}
