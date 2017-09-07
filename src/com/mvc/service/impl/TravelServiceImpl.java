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
import com.mvc.repository.TravelRepository;
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
	@Autowired
	TravelRepository travelRepository;
	//旅游查询
	@Override
	public List<Travel> findTravelAlls() {
		return travelDao.findTravelAlls();
	}

	//旅游交易
	@Override
	public List<TravelTrade> saveTravelTrade(TravelTrade travelTrade) {
		List<TravelTrade> list = travelDao.saveTravelTrade(travelTrade);
		Float trtrprice = travelTrade.getTrtr_price();
		travelTrade.setTrtr_price(Float.valueOf(trtrprice));
		return list;	
	}
	//根据id查找travel
	@Override
	public Travel findTravelById(String travelid) {
		return travelRepository.findTravelById(Integer.parseInt(travelid));
	}
}
