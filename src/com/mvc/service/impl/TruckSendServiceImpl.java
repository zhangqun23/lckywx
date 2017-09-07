package com.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entiy.Truck_send;
import com.mvc.repository.TruckSendRepository;
import com.mvc.service.TruckSendService;

/**
 * 货车主录入信息
 * @author ghl
 * @date   2017年9月6日
 */
@Service("truckSendServiceImpl")
public class TruckSendServiceImpl implements TruckSendService {
	@Autowired
	TruckSendRepository truckSendRepository;
	//添加货车主录入信息
	@Override
	public Truck_send addTruckSend(Truck_send truckSend) {
		Truck_send result = truckSendRepository.saveAndFlush(truckSend);
		if(result.getTrse_id()!=null){
			return result;
		}else{
			return null;
		}
	}

}
