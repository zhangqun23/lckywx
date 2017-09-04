package com.mvc.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.enums.IsDelete;
import com.mvc.dao.BusNeedDao;
import com.mvc.entiy.BusNeed;
import com.mvc.entiy.BusTrade;
import com.mvc.repository.BusNeedRepository;
import com.mvc.repository.BusTradeRepository;
import com.mvc.service.BusNeedService;
import com.utils.StringUtil;

/**
 * 班车
 * 
 * @author wanghuimin
 * @date 2017年8月9日
 */
@Service("busNeedServiceImpl")
public class BusNeedServiceImpl implements BusNeedService {
	@Autowired
	BusNeedRepository busNeedRepository;
	@Autowired
	BusTradeRepository busTradeRepository;
	@Autowired
	BusNeedDao busNeedDao;

	// 添加,修改班车定制需求
	@Override
	public BusNeed saveBusNeed(BusNeed busNeed) {
		BusNeed result = busNeedRepository.saveAndFlush(busNeed);
		if (result.getBune_id() != null) {
			return result;
		} else
			return null;
	}

	// 添加,修改班车定制需求的同时添加交易
	@Override
	public BusTrade saveAndBusTrade(BusTrade result1) {
		BusTrade result = busTradeRepository.saveAndFlush(result1);
		if (result.getButr_id() != null)
			return result;
		else
			return null;
	}

	// 查询班车定制需求
	@Override
	public List<BusNeed> findBusNeedAlls(Map<String, Object> map) {
		return busNeedDao.findByUsertime(map);
	}

	// 添加,修改班车交易
	@Override
	public boolean saveBusTrade(BusTrade busTrade) {
		BusTrade result = busTradeRepository.saveAndFlush(busTrade);
		if (result.getButr_id() != null)
			return true;
		else
			return false;
	}

	// 删除班车定制需求
	@Override
	public boolean deleteBusNeed(Map<String, Object> map) {
		List<BusTrade> list=busNeedDao.findBusTradeAlls(map);
		Integer busNeed_id = (Integer) map.get("busNeed_id");
		boolean out = false;
		if(list!=null){
			out = busNeedDao.deleteBusNeed(busNeed_id);
		}	
		return out;
	}

	// 查看单个班车预定需求，班车定制表
	@Override
	public BusNeed findBusNeedAll(Map<String, Object> map) {
		return busNeedDao.findByBusNeed_id(map);
	}

	// 查看单个班车预定需求，班车交易表
	@Override
	public BusTrade findBusTradeAll(Map<String, Object> map) {
		return busNeedDao.findBusTradeByBusNeed_id(map);
	}
}
