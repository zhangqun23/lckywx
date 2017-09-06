/**
 * @Title: TravelServiceImpl.java
 * @Package com.mvc.service.impl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 */
package com.mvc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.TravelDao;
import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;
import com.mvc.service.TravelService;

/**
 * @ClassName: TravelServiceImpl
 * @Description: 旅游查询
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 * 
 *
 */
@Service("travelServiceImpl")
public class TravelServiceImpl implements TravelService{
	@Autowired 
	TravelDao travelDao;
	//旅游查询
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
	@Override
	public List<TravelTrade> saveTravelTrade(TravelTrade travelTrade) {
		List<TravelTrade> list = travelDao.saveTravelTrade(travelTrade);
		Travel travel = new Travel();
		Float trtrprice = travelTrade.getTrtr_price();
		//trtrprice = (travel.getTravel_discount() * 
		//		((travelTrade.getTrtr_cnum() * travel.getTravel_mprice()) 
		//		+ (travelTrade.getTrtr_mnum() * travel.getTravel_mprice())))
		//		+ travel.getTravel_insurance();
		travelTrade.setTrtr_price(Float.valueOf(trtrprice));
		return list;	
	}
}
