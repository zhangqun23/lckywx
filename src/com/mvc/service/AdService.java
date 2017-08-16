package com.mvc.service;

import com.mvc.entiy.Ad;

/**
 * 
 * 广告
 * @author ghl
 * @date   2017年8月15日
 */
public interface AdService {
	//发布，修改广告信息
	boolean saveAd (Ad ad);
}
