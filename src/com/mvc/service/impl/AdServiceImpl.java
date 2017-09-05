package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.AdDao;
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
	@Autowired
	AdDao adDao;
	//广告添加，修改
	@Override
	public Ad saveAd(Ad ad) {
		Ad result = adRepository.saveAndFlush(ad);
		if (result.getAd_id() != null)
			return result ;
		else
			return null ;
	}
	//类型为空返回全部广告
	@Override
	public List<Ad> finAdAlls() {
		return adRepository.findAlls();
	}
	//返回相应类型广告
	@Override
	public List<Ad> finAdByType(Integer adType) {
		return adRepository.findAdByType(adType);
	}
	//删除广告根据广告id
	@Override
	public Boolean deleteAd(Integer ad_id) {
		return adDao.deleteAd(ad_id);
	}
	//根据id寻找广告
	@Override
	public Ad selectAdverInfo(String adId) {
		int adid = Integer.parseInt(adId);
		return adRepository.findAdById(adid);
	}
	//根据openId，adType，adState查询广告
	@Override
	public List<Ad> findMyPlaceAd(Integer adType, Integer adState, String openId) {
		return adRepository.findMyPlaceAd(adType,adState,openId);
	}
	@Override
	public List<Ad> findMyPlaceAdAll(String openId) {	
		return adRepository.findMyPlaceAdAll(openId);
	}

	
	

}
