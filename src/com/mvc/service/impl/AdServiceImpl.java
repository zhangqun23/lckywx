package com.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mvc.entiy.Ad;
import com.mvc.repository.AdRepository;
import com.mvc.service.AdService;

/**
 * 广告
 * 
 * @author ghl
 * @date 2017年8月15日
 */
@Service("adServiceImpl")
public class AdServiceImpl implements AdService {
	@Autowired
	AdRepository adRepository;

	@Override
	public boolean saveAd(Ad ad) {
		Ad result = adRepository.saveAndFlush(ad);
		if (result.getAd_id() != null)
			return true;
		else
			return false;
	}

}
