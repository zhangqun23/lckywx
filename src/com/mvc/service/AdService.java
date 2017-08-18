package com.mvc.service;

import java.util.List;

import com.mvc.entiy.Ad;

/**
 * 
 * 广告
 * @author ghl
 * @date   2017年8月15日
 */
public interface AdService {
	//发布，修改广告信息
	Ad saveAd (Ad ad);
    //类型为空返回全部广告
	List<Ad> finAdAlls();
	//返回相应类型广告
	List<Ad> finAdByType(Integer adType);
	//删除广告根据广告id
	Boolean deleteAd(Integer ad_id);


}
