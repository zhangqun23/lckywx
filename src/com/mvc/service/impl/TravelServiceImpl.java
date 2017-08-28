/**
 * @Title: TravelServiceImpl.java
 * @Package com.mvc.service.impl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 */
package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.TravelDao;
import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;
import com.mvc.repository.TravelRepository;
import com.mvc.repository.TravelTradeRepository;
import com.mvc.service.TravelService;

/**
 * @ClassName: TravelServiceImpl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 * 
 *
 */
@Service("travelServiceImpl")
public class TravelServiceImpl implements TravelService{
	@Autowired
	TravelRepository travelRepository;
	@Autowired
	TravelTradeRepository travelTradeRepository;
	@Autowired 
	TravelDao travelDao;
	//
	@Override
	public List<Travel> findTravelAlls0(Map<String, Object> map) {
		return travelDao.findTravelAlls0(map);
	}
	//按出发日期查询旅游信息
	@Override
	public List<Travel> findTravelAlls(Map<String, Object> map) {
		List<Travel> list = travelDao.findTravelAlls(map);
		return list;
	}
	//按成人票价查询旅游信息
	@Override
	public List<Travel> findTravelAlls1(Map<String, Object> map) {
		List<Travel> list = travelDao.findTravelAlls1(map);
		return list;
	}
	//旅游交易
	@SuppressWarnings("null")
	@Override
	public List<TravelTrade> saveTravelTrade(TravelTrade travelTrade) {
		List<TravelTrade> list = travelDao.saveTravelTrade(travelTrade);
		Travel travel = null;
		Float trtrprice = travelTrade.getTrtr_price();
		trtrprice = (travel.getTravel_discount() * 
				((travelTrade.getTrtr_cnum() * travel.getTravel_mprice()) 
				+ (travelTrade.getTrtr_mnum() * travel.getTravel_mprice())))
				+ travel.getTravel_insurance();
		travelTrade.setTrtr_price(Float.valueOf(trtrprice));
	//	while (it.hasNext()) {
    //	obj = (Object[]) it.next();
	//  travelTrade = new TravelTrade();
	//  travelTrade.setTrtr_tel(obj[0].toString());
	//  travelTrade.setTrtr_mnum(obj[1].hashCode());
    //  travelTrade.setTrtr_cnum(obj[2].hashCode());
    //  String trtr_price = StringUtil.add(obj[1].toString(), obj[2].toString());
    //  workHouse.setAvg_time_dust(Float.valueOf(avg_time_dust));
		
		return list;	
	}
}
