package com.mvc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.SmallGoodsDao;
import com.mvc.entity.SmallGoods;
import com.mvc.repository.SmallGoodsRepository;
import com.mvc.service.SmallGoodsService;

/**
 * 小件货运
 * 
 * @author lijing
 * @date 2017年8月11日
 */

@Service("smallGoodsServiceImpl")
public class SmallGoodsServiceImpl implements SmallGoodsService {
	@Autowired
	SmallGoodsRepository smallGoodsRepository;
	@Autowired
	SmallGoodsDao smallGoodsDao;

	// 添加,修改小件货运信息
	@Override
	public SmallGoods saveSmallGoods(SmallGoods smallGoods) {
		SmallGoods result = smallGoodsRepository.saveAndFlush(smallGoods);
		if (result.getSmgo_id() != null)
			return result;
		else
			return null;
	}

	// 查询小件货运信息
	@Override
	public List<SmallGoods> findSmallGoodsBy(Date startDate, Date endDate, String openid) {
		return smallGoodsRepository.findByTimeAndPlace(startDate, endDate, openid);
	}

	// 查询小件货运信息
	@Override
	public List<SmallGoods> findSmallGoodsAlls(Map<String, Object> map) {
		return smallGoodsDao.findAllSmallGoods(map);
	}

	// 根据id查找小件货运信息
	@Override
	public SmallGoods findSmallGoodsById(String sgid) {
		return smallGoodsDao.SelectSmallGoodsById(Integer.parseInt(sgid));
	}

}
