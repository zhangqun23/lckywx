package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.mvc.entiy.BusNeed;

/**
 * 
 * @author wanghuimin
 * @date 2017年8月9日
 */
public interface BusNeedDao {

	//根据时间查询班车定制需求
	List<BusNeed> findByUsertime(Map<String, Object> map);

	//删除班车定制需求
	boolean deleteBusNeed(Integer busNeed_id);

	//查看单个班车预定需求，班车定制表
	BusNeed findByBusNeed_id(Map<String, Object> map);


}
