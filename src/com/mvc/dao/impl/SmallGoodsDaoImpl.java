package com.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.SmallGoodsDao;
import com.mvc.entity.SmallGoods;
import com.mvc.entity.TruckSend;

/**
 * 零担货运
 * 
 * @author ghl
 * @date 2017年9月9日
 */
@Repository("SmallGoodsDaoImpl")
public class SmallGoodsDaoImpl implements SmallGoodsDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@Override
	public List<SmallGoods> findAllSmallGoods(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String openId = (String) map.get("openId");
		String state = (String) map.get("state");
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
		selectSql = "select * from small_goods where open_id='" + openId + "' and is_delete=0 and is_finish=" + state
				+ "  order by smgo_id desc  limit " + offset + ", " + limit;
		Query query = em.createNativeQuery(selectSql, SmallGoods.class);
		List<SmallGoods> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public SmallGoods SelectSmallGoodsById(Integer smallGoodsId) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		String selectSql = null;
		selectSql = "select * from small_goods where smgo_id='" + smallGoodsId + "' and is_delete=0 ";
		Query query = em.createNativeQuery(selectSql, SmallGoods.class);
		SmallGoods small = (SmallGoods) query.getSingleResult();
		em.close();
		return small;
	}

}
