package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entiy.Travel;
import com.mvc.repository.TravelRepository;
import com.mvc.service.TravelService;

@Service("/travelServiceImpl")
public class TravelServiceImpl implements TravelService{
	@Autowired
	TravelRepository travelRepository;
	
	//查询旅游信息
		@Override
		public List<Travel> findTravelAlls(String useDate) {
			return travelRepository.findByUsertime(useDate);
		}

}
