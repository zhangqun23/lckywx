package com.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entiy.Driver;
import com.mvc.entiy.Truck;
import com.mvc.entiy.TruckNeed;
import com.mvc.entiy.TruckSend;
import com.mvc.repository.DriverRepository;
import com.mvc.repository.TruckNeedRepository;
import com.mvc.repository.TruckRepository;
import com.mvc.repository.TruckSendRepository;
import com.mvc.service.TruckDriverService;

/**
 * 零担货运
 * @author ghl
 * @date   2017年9月6日
 */
@Service("truckDriverServiceImpl")
public class TruckDriverServiceImpl implements TruckDriverService {
	@Autowired
	DriverRepository driverRepository;
	@Autowired
	TruckRepository truckRepository;
	@Autowired
	TruckSendRepository truckSendRepository;
	@Autowired
	TruckNeedRepository truckNeedRepository;
	//添加货车车主基本信息
	@Override
	public Driver addDriver(Driver driver) {
		Driver result = driverRepository.saveAndFlush(driver);
		if (result.getDriver_id()!=null) {
			return result;
		}else{
			return null;
		}
	}
	//添加货车基本信息
	@Override
	public Truck addTruck(Truck truck) {
		Truck result = truckRepository.saveAndFlush(truck);
		if(result.getTrck_id()!=null){
			return result;
		}else{
			return null;
		}
	}
	//添加货车主录入信息
	@Override
	public TruckSend addTruckSend(TruckSend truckSend) {
		TruckSend result = truckSendRepository.saveAndFlush(truckSend);
		if(result.getTrse_id()!=null){
			return result;
		}else{
			return null;
		}
	}
	//货主信息录入
	@Override
	public TruckNeed addTruckNeed(TruckNeed truckNeed) {
		TruckNeed result = truckNeedRepository.saveAndFlush(truckNeed);
		if (result.getTrne_id()!=null) {
			return result;
		}else{
			return null;
		}
	}
	
}
