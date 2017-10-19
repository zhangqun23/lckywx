package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.TravelTradeDao;
import com.mvc.entity.TravelTrade;

@Repository("travelTradeDaoImpl")
public class TravelTradeDaoImpl implements TravelTradeDao{

	
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TravelTrade> selectMyTravelInfoByOId(String openid, Integer offset, Integer limit, String state) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from travel_trade tt where tt.is_state = '" + state + "' ");
		sql.append(" and tt.open_id = '" +openid+ "' limit :offset, :end");
		
		Query query = em.createNativeQuery(sql.toString(),TravelTrade.class);
		query.setParameter("offset", offset);
		query.setParameter("end", limit);
		List<TravelTrade> list =  query.getResultList();
		em.close();
		return list;
	}

}
