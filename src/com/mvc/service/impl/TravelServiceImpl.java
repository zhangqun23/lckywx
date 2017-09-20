/**
 * @Title: TravelServiceImpl.java
 * @Package com.mvc.service.impl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 */
package com.mvc.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	public List<Travel> findTravelAlls(Integer offset, Integer limit) {
		return travelDao.findTravelAlls(offset, limit);
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

	//根据openid查找旅游信息
	@Override
	public Map selectMyTravelInfoByOId(String openid, Integer offset, Integer limit, String state) {
		List<Object> list= travelDao.selectMyTravelInfoByOId(openid, offset, limit, state);
		Iterator<Object> it = list.iterator();
		Map<String, Object> listMap = new HashMap<String, Object>();
		Object[] obj = null;
		if(list.size() != 0){
			obj = (Object[]) it.next();
			listMap.put("travel_travel_id", obj[0]);
			listMap.put("travel_is_delete", obj[1]);
			listMap.put("travel_travel_content", obj[2]);
			listMap.put("travel_travel_cprice", obj[3]);
			listMap.put("travel_travel_days", obj[4]);
			listMap.put("travel_travel_discount", obj[5]);
			listMap.put("travel_travel_frim", obj[6]);
			listMap.put("travel_travel_insurance", obj[7]);
			listMap.put("travel_travel_left_num", obj[8]);
			listMap.put("travel_travel_location", obj[9]);
			listMap.put("travel_travel_mprice", obj[10]);
			listMap.put("travel_travel_route", obj[11]);
			listMap.put("travel_travel_stime", obj[12]);
			listMap.put("travel_travel_tel", obj[13]);
			listMap.put("travel_travel_title", obj[14]);
			listMap.put("travel_travel_total_num", obj[15]);
			listMap.put("trade_trtr_id", obj[16]);
			listMap.put("trade_is_state", obj[17]);
			listMap.put("trade_open_id", obj[18]);
			listMap.put("trade_trade_discount", obj[19]);
			listMap.put("trade_trtr_cnum", obj[20]);
			listMap.put("trade_trtr_mnum", obj[21]);
			listMap.put("trade_trtr_num", obj[22]);
			listMap.put("trade_trtr_price", obj[23]);
			listMap.put("trade_trtr_tel", obj[24]);
			listMap.put("trade_travel", obj[25]);
		}
		return listMap;
	}
}
