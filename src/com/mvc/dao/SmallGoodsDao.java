package com.mvc.dao;

import java.util.List;
import java.util.Map;

import com.mvc.entity.Driver;
import com.mvc.entity.SmallGoods;
import com.mvc.entity.Truck;
import com.mvc.entity.TruckNeed;
import com.mvc.entity.TruckSend;

/**
 * 零担货运
 * 
 * @author ghl
 * @date 2017年9月9日
 */
public interface SmallGoodsDao {
	// 查询小件快运列表
	List<SmallGoods> findAllSmallGoods(Map<String, Object> map);
	
	SmallGoods SelectSmallGoodsById(Integer smallGoodsId);

}
