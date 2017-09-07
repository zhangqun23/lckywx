package com.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entiy.Driver;
import com.mvc.entiy.Truck;
import com.mvc.repository.DriverRepository;
import com.mvc.repository.TruckRepository;
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

}
