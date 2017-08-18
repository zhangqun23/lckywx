/**
 * @Title: TravelServiceImpl.java
 * @Package com.mvc.service.impl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 */
package com.mvc.service.impl;

import java.util.List;

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
	//按出发日期查询旅游信息
	@Override
	public List<Travel> findTravelAlls() {
		List<Travel> list = travelDao.findTravelAlls();
		return list;
	}
	//按成人票价查询旅游信息
	@Override
	public List<Travel> findTravelAlls1() {
		List<Travel> list = travelDao.findTravelAlls1();
		return list;
	}
	//旅游交易
	@SuppressWarnings(value = { })
	@Override
	public List<TravelTrade> saveTravelTrade(TravelTrade travelTrade) {
		List<TravelTrade> result =travelDao.saveTravelTrade(travelTrade);
		if (((TravelTrade) result).getTrtr_id() != null)//强制转换result类型
			return result;
		return result;
	}

}
