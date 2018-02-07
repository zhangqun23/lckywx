package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.TravelTradeDao;
import com.mvc.service.WxReturnPayService;


@Service("wxPayServiceImpl")
public class WxReturnPayServiceImpl implements WxReturnPayService{

	@Autowired 
	TravelTradeDao travelTradeDao;
	
	@Override	
	public void saveTradeState(String trade_num, String transaction_id, String datanow){
		travelTradeDao.saveTradeState(trade_num, transaction_id, datanow);
	}
//
//	@Override
//	public Float getTotalNum(String out_trade_no) {
//		TravelTrade traveltrade = wxPayRepository.getTotalNum(out_trade_no);
//		Travel travel = traveltrade.getTravel();
//		int travel_left_num = travel.getTravel_left_num();
//		int mnun = traveltrade.getTrtr_mnum();
//		int cnum = traveltrade.getTrtr_cnum();
//		int left_num = travel_left_num - mnun - cnum;
//		if(left_num <0){
//			left_num = 0;
//		}
//		travelRepository.updateTravel(left_num,travel.getTravel_id());
//		return travel.getTravel_discount();
//	}

	@Override
	public int getTravelTrade(String out_trade_no) {
		List<Object> trade = travelTradeDao.getTravelTrade(out_trade_no);
		if(trade.size() == 0){
			return 0;
		}else{
			return 1;
		}
	}
}
