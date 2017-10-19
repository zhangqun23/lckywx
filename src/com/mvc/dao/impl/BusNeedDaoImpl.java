 package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.enums.IsDelete;
import com.mvc.dao.BusNeedDao;
import com.mvc.entiy.BusNeed;
import com.mvc.entiy.BusTrade;

/**
  * 
  * @author wanghuimin
  * @date 2017年8月9日
  */
@Repository("busNeedDaoImpl")
public class BusNeedDaoImpl implements BusNeedDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	//根据时间查询班车定制需求
	@SuppressWarnings("unchecked")
	@Override
	public List<BusNeed> findByUsertime(Map<String, Object> map) {
		String startDate = null;
		String endDate = null;
		String openid = null;
		if ((String) map.get("startDate")!=null) {
			startDate=(String) map.get("startDate");//开始时间
		}
		if ((String) map.get("endDate")!=null) {
			endDate=(String) map.get("endDate");//结束时间
		}
		if ((String) map.get("openid")!=null) {
			openid=(String) map.get("openid");//结束时间
		}
		EntityManager em=emf.createEntityManager(); 
		String sql;
		if(startDate==null || endDate==null ){
			sql="select * from bus_need where open_id='"+ openid +"' and is_delete=1 order by bune_gath_time desc ";		

		}else {
			sql="select * from bus_need where open_id='"+ openid +"' and is_delete=1 and "
					+ " bune_gath_time between '"+ startDate +"' and '"+ endDate +"' order by bune_gath_time desc ";		
		}
		Query query=em.createNativeQuery(sql,BusNeed.class);
		List<BusNeed> list=query.getResultList();
		em.close();
		return list;
	}
	
	//删除班车定制需求
	@Override
	public boolean deleteBusNeed(Integer busNeed_id) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			String selectSql = " update bus_need set is_delete =:is_delete where user_id =:user_id ";
			Query query = em.createNativeQuery(selectSql);			
			query.setParameter("user_isdelete", IsDelete.NO.value);
			query.setParameter("user_id", busNeed_id);
			query.executeUpdate();
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return true;
	}

	//查看单个班车预定需求
	@SuppressWarnings("unchecked")
	@Override
	public List<BusTrade> findBusTradeAlls(Map<String, Object> map) {
		String startDate = null;
		String endDate = null;
		Integer busNeed_id = null;
		String openid = null;
		if ((String) map.get("startDate")!=null) {
			startDate=(String) map.get("startDate");//开始时间
		}
		if ((String) map.get("endDate")!=null) {
			endDate=(String) map.get("endDate");//结束时间
		}
		if ((String) map.get("openid")!=null) {
			openid=(String) map.get("openid");//结束时间
		}
		if(map.get("busNeed_id")!=null){
			busNeed_id=(Integer) map.get("busNeed_id");
		}
		EntityManager em=emf.createEntityManager(); 
		String sql;
		if(startDate==null || endDate==null ){
			sql="select * from bus_trade where bune_id=:bune_id and open_id=:openid and is_delete=1 order by butr_time desc ";		

		}else {
			sql="select * from bus_trade where bune_id=:bune_id and open_id=:openid and is_delete=1 and "
					+ " butr_time between '"+ startDate +"' and '"+ endDate +"' order by butr_time desc ";		
		}
		Query query=em.createNativeQuery(sql,BusTrade.class);
		query.setParameter("bune_id", busNeed_id);
		query.setParameter("openid", openid);
		List<BusTrade> list=query.getResultList();
		em.close();
		return list;
	}

	//查看单个班车预定需求,班车定制表
	@SuppressWarnings("unchecked")
	@Override
	public BusNeed findByBusNeed_id(Map<String, Object> map) {
		Integer busNeed_id = null;
		if(map.get("busNeed_id")!=null){
			busNeed_id=(Integer) map.get("busNeed_id");
		}
		EntityManager em=emf.createEntityManager(); 
		String sql=	"select * from bus_need where bune_id=:busNeed_id and is_delete=1 ";
	
		Query query=em.createNativeQuery(sql,BusNeed.class);
		query.setParameter("busNeed_id", busNeed_id);
		BusNeed list=(BusNeed) query.getSingleResult();
		em.close();
		return list;
	}

	//查看单个班车预定需求,班车交易表
	@SuppressWarnings("unchecked")
	@Override
	public BusTrade findBusTradeByBusNeed_id(Map<String, Object> map) {
		Integer busNeed_id = null;
		if(map.get("busNeed_id")!=null){
			busNeed_id=(Integer) map.get("busNeed_id");
		}
		EntityManager em=emf.createEntityManager(); 
		String sql=	"select * from bus_trade where bune_id=:busNeed_id and is_delete=1 ";
	
		Query query=em.createNativeQuery(sql,BusTrade.class);
		query.setParameter("bune_id", busNeed_id);
		BusTrade result=(BusTrade) query.getSingleResult();
		int in=query.getFirstResult();
		em.close();
		return result;
	}

}
