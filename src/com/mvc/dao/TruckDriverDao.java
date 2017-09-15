package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.mvc.entiy.TruckNeed;
import com.mvc.entiy.TruckSend;

/**
 * 零担货运
 * @author ghl
 * @date   2017年9月9日
 */
public interface TruckDriverDao {
	//货主查询车辆根据目的地，出发时间
	List<TruckSend> finByUsertime(Map<String, Object> map);
	//车主查询货源根据始发地、目的地，出发时间
	List<TruckNeed> findByUsertime(Map<String, Object> map);

}
