package com.mvc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mvc.entity.SmallGoods;
/**
 * 小件货运
 * @author lijing
 * @date 2017年8月11日
 */
public interface SmallGoodsService {
	// 添加,修改小件货运信息
	SmallGoods saveSmallGoods(SmallGoods smallGodds);

	// 查询小件货运信息 
	List<SmallGoods> findSmallGoodsBy(Date date1, Date date2, String openid);
	
	// 根据id查询小件货运信息 
	SmallGoods findSmallGoodsById(String sgid);
	
	// 查询所有小件货运信息 
	List<SmallGoods> findSmallGoodsAlls(Map<String, Object> map);

}
