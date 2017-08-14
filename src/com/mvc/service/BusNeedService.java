package com.mvc.service;

import java.util.List;

import com.mvc.entiy.BusNeed;
import com.mvc.entiy.BusTrade;

/**
 * 班车
 * @author wanghuimin
 * @date 2017年8月9日
 */
public interface BusNeedService {

	// 添加,修改班车定制需求
	boolean saveBusNeed(BusNeed busNeed);

	// 查询班车定制需求
	List<BusNeed> findBusNeedAlls(String useDate);

	// 添加,修改班车交易
	boolean saveBusTrade(BusTrade busTrade);

}
