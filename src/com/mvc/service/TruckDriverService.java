package com.mvc.service;

import com.mvc.entiy.Driver;
import com.mvc.entiy.Truck;
import com.mvc.entiy.Truck_need;
import com.mvc.entiy.Truck_send;

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
	//添加货车主录入信息
	Truck_send addTruckSend(Truck_send truckSend);
	//货主信息录入
	Truck_need addTruckNeed(Truck_need truckNeed);
}
