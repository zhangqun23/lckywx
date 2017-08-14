/**
 * @Title: TravelServiceImpl.java
 * @Package com.mvc.service.impl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 */
package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entiy.Travel;
import com.mvc.repository.TravelRepository;
import com.mvc.service.TravelService;

/**
 * @ClassName: TravelServiceImpl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 * 
 *
 */
@Service("travelServiceImpl")
public class TravelServiceImpl implements TravelService{
	@Autowired
	TravelRepository travelRepository;
	
	@Override
	public List<Travel> findTravelAlls(String useDate) {
		return travelRepository.findByUsertime(useDate);
	}

}
