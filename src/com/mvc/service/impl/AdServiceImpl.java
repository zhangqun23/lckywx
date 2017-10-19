package com.mvc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.AdDao;
import com.mvc.entity.Ad;
import com.mvc.repository.AdRepository;
import com.mvc.service.AdService;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

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
	//修改广告
	@Override
	public Ad saveAdRpeat(JSONObject jsonObject, Integer adId) {
		Ad ad = adRepository.findAdById(adId);
		if (ad!=null) {
			try {
				if (jsonObject.containsKey("ad_type")){
					if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_type"))){
					ad.setAd_type(Integer.parseInt(jsonObject.getString("ad_type")));
					}
				}
				if (jsonObject.containsKey("ad_name")){
					if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_name"))){
						ad.setAd_name(jsonObject.getString("ad_name"));
					}
				}
				if (jsonObject.containsKey("ad_tel")){
					if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_tel"))){
						ad.setAd_tel(jsonObject.getString("ad_tel"));
					}	
				}
				if (jsonObject.containsKey("ad_title")){
					if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_title"))){
						ad.setAd_title(jsonObject.getString("ad_title"));
					}
				}
			/*	if (jsonObject.containsKey("ad_pic_path")){
					if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_pic_path"))){
						ad.setAd_pic_path(jsonObject.getString("ad_pic_path"));
					}
				}*/
				if (jsonObject.containsKey("ad_remark")){
					if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_remark"))){
						ad.setAd_remark(jsonObject.getString("ad_remark"));
					}else{
						ad.setAd_remark("");
					}
				}
				if (jsonObject.containsKey("ad_content")){
					if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_content"))){
						ad.setAd_content(jsonObject.getString("ad_content"));
					}
				}
				ad.setAd_state(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ad = adRepository.saveAndFlush(ad);
		if (ad.getAd_id()!=null) {
			return ad;
		}else{
			return null;
		}
	}
	//返回相应类型广告
	@Override
	public List<Ad> finAdByType(Integer adType, Integer offset, Integer limit) {
		return adDao.finAdByType(adType,offset,limit);
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
	public List<Ad> findMyPlaceAd(Integer adState, String openId,Integer offset, Integer limit) {
		return adDao.findMyPlaceAd(adState,openId,offset,limit);
	}


}
