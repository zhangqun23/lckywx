package com.mvc.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.mvc.dao.AdDao;

/**
 * 广告
 * 
 * @author ghl
 * @date   2017年8月15日
 */
@Repository("adDaoImpl")
public class AdDaoImpl implements AdDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	//删除广告根据广告id
	@Override
	public Boolean deleteAd(Integer ad_id) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Integer adid = ad_id;
			String selectSql = "update ad set ad.is_delete=0 where ad.ad_id = "+ adid;
			Query query =  em.createNativeQuery(selectSql);
			query.executeUpdate();
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return true;
	}

}
