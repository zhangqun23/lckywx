package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.TruckDriverDao;
import com.mvc.entity.TruckNeed;
import com.mvc.entity.TruckSend;

/**
 * 零担货运
 * @author ghl
 * @date   2017年9月9日
 */
@Repository("truckDriverDaoImpl")
public class TruckDriverDaoImpl implements TruckDriverDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	
	//货主查询车辆根据目的地，出发时间
	@Override
	public List<TruckSend> finByUsertime(Map<String, Object> map) {
		String trse_eplace = null;
		String startTime = null;
		String endTime = null;
		if (map.get("trse_eplace") != null) {
			trse_eplace = (String) map.get("trse_eplace");//目的地
		}
		if (map.get("startTime") != null) {
			startTime = (String) map.get("startTime");//起始时间
		}
		if (map.get("endTime") != null) {
			endTime = (String) map.get("endTime");//结束时间
		}
		EntityManager em = emf.createEntityManager();
		String selectSql = null;
		 if (startTime==null || endTime==null) {
			 selectSql = "select * from truck_send where trse_eplace='"+ trse_eplace +"' order by trse_time desc  ";
			 		
		}else{
			 selectSql = "select * from truck_send where trse_eplace='"+ trse_eplace +"'  "
				 		+ "and trse_time between '"+ startTime +"' and '"+ endTime +"' order by trse_time desc ";
		}
		 Query query = em.createNativeQuery(selectSql, TruckSend.class);
		 List<TruckSend> list = query.getResultList();
		 em.close();
		return list;
	}
	//车主查询货源根据始发地、目的地，出发时间
	@Override
	public List<TruckNeed> findByUsertime(Map<String, Object> map) {
		String trne_eplace = null;
		String startTime = null;
		String endTime = null;
		if (map.get("trne_eplace") != null) {
			trne_eplace = (String) map.get("trne_eplace");//目的地
		}
		if (map.get("startTime") != null) {
			startTime = (String) map.get("startTime");//起始时间
		}
		if (map.get("endTime") != null) {
			endTime = (String) map.get("endTime");//结束时间
		}
		EntityManager em = emf.createEntityManager();
		String selectSql = null;
		 if (startTime==null || endTime==null) {
			 selectSql = "select * from truck_need where trne_eplace='"+ trne_eplace +"' order by trne_time desc ";
			 		
		}else {
			 selectSql = "select * from truck_need where trne_eplace='"+ trne_eplace +"'  "
				 		+ "and trne_time between '"+ startTime +"' and '"+ endTime +"' order by trne_time desc ";
		}
		 Query query = em.createNativeQuery(selectSql, TruckNeed.class);
		 List<TruckNeed> list = query.getResultList();
		 em.close();
		return list;
	}

}
