package com.mvc.dao;

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
 * @date 2017年9月9日
 */
public interface TruckDriverDao {
	// 车主查询车辆根据目的地，出发时间
	List<TruckSend> findTruckSendList(Map<String, Object> map);

	// 货主查询车辆根据目的地，出发时间
	List<TruckSend> findNewTruckSendList(Map<String, Object> map);

	// 货主查询货源根据始发地、目的地，出发时间
	List<TruckNeed> findByUsertime(Map<String, Object> map);

	List<Truck> selectUserTruck(String openId);

	// 车主查询货源根据始发地、目的地，出发时间
	List<TruckNeed> findNewTruckNeed(Map<String, Object> map);

	Driver selectDriverByOpenId(String openId);

}
