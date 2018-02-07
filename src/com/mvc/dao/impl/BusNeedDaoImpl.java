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
import com.mvc.entity.BusNeed;

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

	// 根据时间查询班车定制需求
	@SuppressWarnings("unchecked")
	@Override
	public List<BusNeed> findByUsertime(Map<String, Object> map) {
		String startDate = null;
		String endDate = null;
		String openid = null;
		int offset = 0;
		int limit = 0;
		int state = 0;
		if ((String) map.get("startDate") != null) {
			startDate = (String) map.get("startDate");// 开始时间
		}
		if ((String) map.get("endDate") != null) {
			endDate = (String) map.get("endDate");// 结束时间
		}
		if ((String) map.get("openid") != null) {
			openid = (String) map.get("openid");// 结束时间
		}
		if (map.get("offset") != null) {
			offset = (int) map.get("offset");
		}
		if (map.get("limit") != null) {
			limit = (int) map.get("limit");
		}
		if ((String) map.get("state") != null) {
			state = Integer.parseInt((String) map.get("state"));
		}
		EntityManager em = emf.createEntityManager();
		String sql;
		if (startDate == null || endDate == null || startDate.equals("") || endDate.equals("")) {
			sql = "select * from bus_need where open_id='" + openid
					+ "' and is_delete=0 and butr_state="+state+" order by bune_insert_time desc limit " + offset + ", " + limit;

		} else {
			sql = "select * from bus_need where open_id='" + openid + "' and is_delete=0 and "
					+ " bune_gath_time between '" + startDate + "' and '" + endDate
					+ "' and butr_state="+state+" order by bune_insert_time desc limit " + offset + ", " + limit;
		}
		Query query = em.createNativeQuery(sql, BusNeed.class);
		List<BusNeed> list = query.getResultList();
		em.close();
		return list;
	}

	// 删除班车定制需求
	@Override
	public boolean deleteBusNeed(Integer busNeed_id) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			String selectSql = " update bus_need set is_delete =:is_delete where user_id =:user_id ";
			Query query = em.createNativeQuery(selectSql);
			query.setParameter("user_isdelete", IsDelete.YES.value);
			query.setParameter("user_id", busNeed_id);
			query.executeUpdate();
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return true;
	}

	// 查看单个班车预定需求,班车定制表
	@SuppressWarnings("unchecked")
	@Override
	public BusNeed findByBusNeed_id(Map<String, Object> map) {
		Integer busNeed_id = null;
		if (map.get("busNeed_id") != null) {
			busNeed_id = (Integer) map.get("busNeed_id");
		}
		EntityManager em = emf.createEntityManager();
		String sql = "select * from bus_need where bune_id=:busNeed_id and is_delete=0 ";

		Query query = em.createNativeQuery(sql, BusNeed.class);
		query.setParameter("busNeed_id", busNeed_id);
		BusNeed list = (BusNeed) query.getSingleResult();
		em.close();
		return list;
	}

}
