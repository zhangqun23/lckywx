package com.mvc.service;

import com.mvc.entiy.Driver;
import com.mvc.entiy.Truck;

/**
 * 零担货运
 * @author ghl
 * @date   2017年9月6日
 */
public interface TruckDriverService {
	//添加货车车主基本信息
	Driver addDriver(Driver driver);
	//添加货车基本信息
	Truck addTruck(Truck truck);

}
