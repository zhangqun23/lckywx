package com.mvc.service;

import java.util.Date;
import java.util.List;


import com.mvc.entiy.SmallGoods;;
/**
 * 小件货运
 * @author lijing
 * @date 2017年8月11日
 */
public interface SmallGoodsService {
	// 添加,修改小件货运信息
	boolean saveSmallGoods(SmallGoods smallGodds);

	// 查询小件货运信息 
	List<SmallGoods> findSmallGoodsAlls(String endPlace);

}
