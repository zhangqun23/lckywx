package com.mvc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.TruckDriverDao;
import com.mvc.entity.Driver;
import com.mvc.entity.Truck;
import com.mvc.entity.TruckNeed;
import com.mvc.entity.TruckSend;
import com.mvc.repository.DriverRepository;
import com.mvc.repository.TruckNeedRepository;
import com.mvc.repository.TruckRepository;
import com.mvc.repository.TruckSendRepository;
import com.mvc.service.TruckDriverService;

/**
 * 零担货运
 * @author ghl
 * @date   2017年9月6日
 */
@Service("truckDriverServiceImpl")
public class TruckDriverServiceImpl implements TruckDriverService {
	@Autowired
	DriverRepository driverRepository;
	@Autowired
	TruckRepository truckRepository;
	@Autowired
	TruckSendRepository truckSendRepository;
	@Autowired
	TruckNeedRepository truckNeedRepository;
	@Autowired
	TruckDriverDao truckDriverDao;
	
	//添加货车车主基本信息
	@Override
	public Driver addDriver(Driver driver) {
		Driver result = driverRepository.saveAndFlush(driver);
		if (result.getDriver_id()!=null) {
			return result;
		}else{
			return null;
		}
	}
	//添加货车基本信息
	@Override
	public Truck addTruck(Truck truck) {
		Truck result = truckRepository.saveAndFlush(truck);
		if(result.getTrck_id()!=null){
			return result;
		}else{
			return null;
		}
	}
	//添加货车主录入信息
	@Override
	public TruckSend addTruckSend(TruckSend truckSend) {
		TruckSend result = truckSendRepository.saveAndFlush(truckSend);
		if(result.getTrse_id()!=null){
			return result;
		}else{
			return null;
		}
	}
	//货主信息录入
	@Override
	public TruckNeed addTruckNeed(TruckNeed truckNeed) {
		TruckNeed result = truckNeedRepository.saveAndFlush(truckNeed);
		if (result.getTrne_id()!=null) {
			return result;
		}else{
			return null;
		}
	}
	//货主查询车辆根据目的地，出发时间
	@Override
	public List<TruckSend> findTruckSend(Map<String, Object> map) {
		return truckDriverDao.finByUsertime(map);
	}
	//车主查询货源根据始发地、目的地，出发时间
	@Override
	public List<TruckNeed> findTruckNeed(Map<String, Object> map) {
		return truckDriverDao.findByUsertime(map);
	}
	//根据openid查询truck对象
	@Override
	public Truck findTruck(String openId) {
		return truckRepository.findTruck(openId);
	}
	//根据truckSendId查询truckSend详情
	@Override
	public TruckSend findTruckSendInfo(Integer trseId) {
		return truckSendRepository.findTruckSendInfo(trseId);
	}
	//根据truckNeedId查询truckNeed详情
	@Override
	public TruckNeed findTruckNeedInfo(Integer trneId) {
		return truckNeedRepository.findTruckNeedInfo(trneId);
	}
	//根据trck_id查询truck
	@Override
	public Truck findTrck(Integer trckId) {
		return truckRepository.findTruck(trckId);
	}

	@Override
	public Driver findDriver(Integer driverId) {
		return driverRepository.findDriver(driverId);
	}
	




	
}
