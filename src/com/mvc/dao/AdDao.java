package com.mvc.dao;

import java.util.List;

import com.mvc.entiy.Ad;

/**
 * 
 * 广告
 * @author ghl
 * @date   2017年8月15日
 */

public interface AdDao {
	//删除广告根据广告id
	Boolean deleteAd(Integer ad_id);
	//查询广告 和分页
	List<Ad> finAdByType(Integer adType, Integer offset, Integer limit);
	//查询我的发布和分页
	List<Ad> findMyPlaceAd(Integer adState, String openId, Integer offset, Integer limit);

}
