package com.mvc.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.TruckDriverDao;
import com.mvc.entity.Driver;
import com.mvc.entity.Truck;
import com.mvc.entity.TruckNeed;
import com.mvc.entity.TruckSend;

/**
 * 零担货运
 * 
 * @author ghl
 * @date 2017年9月9日
 */
@Repository("truckDriverDaoImpl")
public class TruckDriverDaoImpl implements TruckDriverDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	// 货主查询车辆根据目的地，出发时间
	@Override
	public List<TruckSend> findTruckSendList(Map<String, Object> map) {
		int truckId = (int) map.get("truckId");
		EntityManager em = emf.createEntityManager();
		int offset = 0;
		int limit = 10;
		if (map.get("offset") != null) {
			offset = (int) map.get("offset");
		}
		if (map.get("limit") != null) {
			limit = (int) map.get("limit");
		}

		String selectSql = null;
		selectSql = "select * from truck_send where trck_id='" + truckId + "' order by trse_id desc  limit " + offset
				+ ", " + limit;
		Query query = em.createNativeQuery(selectSql, TruckSend.class);
		List<TruckSend> list = query.getResultList();
		em.close();
		return list;
	}

	// 车主查询货源根据始发地、目的地，出发时间
	@Override
	public List<TruckNeed> findByUsertime(Map<String, Object> map) {
		String openId = null;
		int offset = 0;
		int limit = 10;
		if (map.get("openId") != null) {
			openId = (String) map.get("openId");// 目的地
		}
		if (map.get("offset") != null) {
			offset = (int) map.get("offset");// 起始时间
		}
		if (map.get("limit") != null) {
			limit = (int) map.get("limit");// 结束时间
		}
		EntityManager em = emf.createEntityManager();
		String selectSql = null;

		selectSql = "select * from truck_need where open_id='" + openId + "' order by trne_insert_time desc limit "
				+ offset + ", " + limit;

		Query query = em.createNativeQuery(selectSql, TruckNeed.class);
		List<TruckNeed> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<Truck> selectUserTruck(String openId) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		String sql = "select * from truck where open_id= '" + openId + "' and is_delete=0 order by trck_id desc";
		Query query = em.createNativeQuery(sql, Truck.class);
		List<Truck> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<TruckSend> findNewTruckSendList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		int offset = 0;
		int limit = 10;
		if (map.get("offset") != null) {
			offset = (int) map.get("offset");
		}
		if (map.get("limit") != null) {
			limit = (int) map.get("limit");
		}
		String selectSql = null;
		selectSql = "select * from truck_send where trse_time > now() order by trse_time desc  limit " + offset + ", "
				+ limit;
		Query query = em.createNativeQuery(selectSql, TruckSend.class);
		List<TruckSend> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public List<TruckNeed> findNewTruckNeed(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int offset = 0;
		int limit = 10;
		if (map.get("offset") != null) {
			offset = (int) map.get("offset");// 起始时间
		}
		if (map.get("limit") != null) {
			limit = (int) map.get("limit");// 结束时间
		}
		EntityManager em = emf.createEntityManager();
		String selectSql = null;

		selectSql = "select * from truck_need where trne_time>now() order by trne_time desc limit " + offset + ", "
				+ limit;

		Query query = em.createNativeQuery(selectSql, TruckNeed.class);
		List<TruckNeed> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public Driver selectDriverByOpenId(String openId) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		String selectSql = null;

		selectSql = "select * from driver where open_id='" + openId + "'";

		Query query = em.createNativeQuery(selectSql, Driver.class);
		List<Driver> list = query.getResultList();
		em.close();
		Driver driver = null;
		if (list.size() > 0) {
			driver = list.get(0);
		}
		return driver;
	}

}
