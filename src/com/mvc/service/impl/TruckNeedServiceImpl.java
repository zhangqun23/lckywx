package com.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entiy.Truck_need;
import com.mvc.repository.TruckNeedRepository;
import com.mvc.service.TruckNeedService;
/**
 * 货主信息录入
 * @author ghl
 * @date   2017年9月6日
 */
@Service("truckNeedServiceImpl")
public class TruckNeedServiceImpl implements TruckNeedService {
	@Autowired
	TruckNeedRepository truckNeedRepository;
	//货主信息录入
	@Override
	public Truck_need addTruckNeed(Truck_need truckNeed) {
		Truck_need result = truckNeedRepository.saveAndFlush(truckNeed);
		if (result.getTrne_id()!=null) {
			return result;
		}else{
			return null;
		}
		
	}

}
