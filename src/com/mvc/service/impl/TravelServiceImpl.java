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
import org.springframework.transaction.annotation.Transactional;

import com.mvc.dao.TravelDao;
import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;
import com.mvc.repository.TravelRepository;
import com.mvc.repository.TravelTradeRepository;
import com.mvc.service.TravelService;

/**
 * @ClassName: TravelServiceImpl
 * @Description: 旅游查询
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 * 
 *
 */
@Transactional
@Service("travelServiceImpl")
public class TravelServiceImpl implements TravelService{
	@Autowired 
	TravelDao travelDao;
	@Autowired
	TravelRepository travelRepository;
	@Autowired
	TravelTradeRepository travelTradeRepository;
	//旅游查询
	@Override
	public List<Travel> findTravelAlls() {
		return travelDao.findTravelAlls();
	}

	//旅游交易
	@Override
	public TravelTrade saveTravelTrade(TravelTrade travelTrade) {
		TravelTrade result = travelTradeRepository.saveAndFlush(travelTrade);
		return result;
	}

	//根据id查找travel
	@Override
	public Travel findTravelById(String travelid) {
		return travelRepository.findTravelById(Integer.parseInt(travelid));
	}

	@Override
	public void updateTravel(String travel_id, Integer total_num) {
		Travel travel = travelRepository.findTravelById(Integer.parseInt(travel_id));
		Integer left_num = travel.getTravel_left_num() - total_num;
		travelRepository.updateTravel(left_num, Integer.parseInt(travel_id));
		
	}
}
