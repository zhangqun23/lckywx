package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.mvc.dao.AdDao;
import com.mvc.entity.Ad;

/**
 * 广告
 * 
 * @author ghl
 * @date 2017年8月15日
 */
@Repository("adDaoImpl")
public class AdDaoImpl implements AdDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	// 删除广告根据广告id
	@Override
	public Boolean deleteAd(Integer ad_id) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Integer adid = ad_id;
<<<<<<< HEAD
			String selectSql = "update ad set ad.is_delete=1 where ad.ad_id = "+ adid;
			Query query =  em.createNativeQuery(selectSql);
=======
			String selectSql = "update ad set ad.is_delete=1 where ad.ad_id = " + adid;
			Query query = em.createNativeQuery(selectSql);
>>>>>>> dca8b9f6af394949d3c6f5a7f5ec148ebfa85a5b
			query.executeUpdate();
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return true;
	}

	// 查询广告和分页
	@SuppressWarnings("unchecked")
	@Override
	public List<Ad> finAdByType(Integer adType, Integer offset, Integer limit) {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from ad a where a.ad_type = " + adType
				+ " and a.is_delete=0 and a.ad_state=1 order by ad_stime desc limit :offset, :end";
		Query query = em.createNativeQuery(sql, Ad.class);
		query.setParameter("offset", offset);
		query.setParameter("end", limit);
		List<Ad> list = query.getResultList();
		em.close();
		return list;
	}

	// 查询我的发布和分页
	@SuppressWarnings("unchecked")
	@Override
	public List<Ad> findMyPlaceAd(Integer adState, String openId, Integer offset, Integer limit) {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from ad where ad_state = " + adState + " and open_id = '" + openId
				+ "' and is_delete=0 order by ad_stime desc limit " + offset + ", " + limit;
		Query query = em.createNativeQuery(sql, Ad.class);
		/*query.setParameter("offset", offset);
		query.setParameter("end", limit);*/
		List<Ad> list = query.getResultList();
		em.close();
		return list;
	}

}
