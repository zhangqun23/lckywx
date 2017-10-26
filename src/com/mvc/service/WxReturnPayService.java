package com.mvc.service;

public interface WxReturnPayService {

	void saveTradeState(String trade_num, String transaction_id, String datanow);

	int getTravelTrade(String out_trade_no);

}
