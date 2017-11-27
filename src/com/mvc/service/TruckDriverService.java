package com.mvc.service;

import java.util.List;
import java.util.Map;

import com.mvc.entity.Driver;
import com.mvc.entity.Truck;
import com.mvc.entity.TruckNeed;
import com.mvc.entity.TruckSend;

/**
 * 零担货运
 * 
 * @author ghl
 * @date 2017年9月6日
 */
public interface TruckDriverService {
	// 添加货车车主基本信息
	Driver addDriver(Driver driver);

	// 添加货车基本信息
	Truck addTruck(Truck truck);

	// 添加货车主录入信息
	TruckSend addTruckSend(TruckSend truckSend);

	// 货主信息录入
	TruckNeed addTruckNeed(TruckNeed truckNeed);

	// 车主查询车辆根据目的地，出发时间
	List<TruckSend> findTruckSendList(Map<String, Object> map);

	// 车主查询车辆根据目的地，出发时间
	List<TruckSend> findNewTruckSendList(Map<String, Object> map);

	// 货主查询货源根据始发地、目的地，出发时间
	List<TruckNeed> findTruckNeed(Map<String, Object> map);

	// 车主查询货源根据始发地、目的地，出发时间
	List<TruckNeed> findNewTruckNeed(Map<String, Object> map);

	// 根据openid查询truck对象
	Truck findTruck(String openId);

	// 根据truckSendId查询truckSend详情
	TruckSend findTruckSendInfo(Integer trseId);

	// 根据truckNeedId查询truckNeed详情
	TruckNeed findTruckNeedInfo(Integer trneId);
	// 根据trck_id查询truck

	Driver findDriver(Integer driverId);

	Truck findTrck(Integer trckId);

	List<Truck> selectUserTruck(String openId);

	Driver selectDriverByOpenId(String openId);

}
