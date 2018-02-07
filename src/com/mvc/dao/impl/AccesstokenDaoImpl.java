package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.AccesstokenDao;
import com.mvc.entity.AccessToken;

@Repository("accesstokenDaoImpl")
public class AccesstokenDaoImpl implements AccesstokenDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AccessToken> getAccesstoken() {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from access_token";
		Query query = em.createNativeQuery(sql, AccessToken.class);
		List<AccessToken> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public void updateAccesstoken(String jsapi_ticket, long now_timestamp, int id) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			String sql = "update access_token set acto_num = '"+ jsapi_ticket+"' , acto_time = '"+now_timestamp+"' where acto_id = "+id ;
			Query query =  em.createNativeQuery(sql);
			query.executeUpdate();
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

}
