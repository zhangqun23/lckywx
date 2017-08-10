package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entiy.BusNeed;
import com.mvc.entiy.BusTrade;
import com.mvc.repository.BusNeedRepository;
import com.mvc.repository.BusTradeRepository;
import com.mvc.service.BusNeedService;


/**
 * 班车
 * @author wanghuimin
 * @date 2017年8月9日
 */

@Service("busNeedServiceImpl")
public class BusNeedServiceImpl implements BusNeedService {
	@Autowired
	BusNeedRepository busNeedRepository;
	@Autowired
	BusTradeRepository busTradeRepository;

	//添加,修改班车定制需求
	@Override
	public boolean saveBusNeed(BusNeed busNeed) {
		BusNeed result = busNeedRepository.saveAndFlush(busNeed);
		if (result.getBune_id() != null)
			return true;
		else
			return false;
	}

	//查询班车定制需求
	@Override
	public List<BusNeed> findBusNeedAlls(String useDate) {
		return busNeedRepository.findByUsertime(useDate);
	}

	//添加,修改班车交易
	@Override
	public boolean saveBusTrade(BusTrade busTrade) {
		BusTrade result =busTradeRepository.saveAndFlush(busTrade);
		if (result.getButr_id() != null)
			return true;
		else
			return false;
	}

}
