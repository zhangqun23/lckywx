package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.mvc.entiy.Truck_need;
import com.mvc.entiy.Truck_send;

/**
 * 零担货运
 * @author ghl
 * @date   2017年9月9日
 */
public interface TruckDriverDao {
	//货主查询车辆根据目的地，出发时间
	List<Truck_send> finByUsertime(Map<String, Object> map);
	//车主查询货源根据始发地、目的地，出发时间
	List<Truck_need> findByUsertime(Map<String, Object> map);

}
