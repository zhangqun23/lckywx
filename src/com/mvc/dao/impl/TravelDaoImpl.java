/**
 * @Title: TravelDaoImpl.java
 * @Package com.mvc.dao.impl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:56:56 
 */
package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.TravelDao;
import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;

/**
 * @ClassName: TravelDaoImpl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:56:56 
 * 
 *
 */
@Repository("travelDaoImpl")
public class TravelDaoImpl implements TravelDao{
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	//按时间查询旅游信息
	@SuppressWarnings("unchecked")
	@Override
	public List<Travel> findTravelAlls(String useDate) {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from travel where is_delete=0";
		//判断useDate是否为空
		if (null != useDate) {
			sql += "and (travel_stime like '%" + useDate + "%') ";
		}
		Query query = em.createNativeQuery(sql.toString());
		List<Travel> list = query.getResultList();
		em.close();
		return list;
		}
	 //按成人票价格查询旅游信息
	@SuppressWarnings("unchecked")
	public List<Travel> findTravelAlls1(String usePrice) {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from travel where is_delete=0";
		//判断useDate是否为空
		if (null != usePrice) {
			sql += "and (travel_mprice like '%" + usePrice + "%') ";
		}
		Query query = em.createNativeQuery(sql.toString());
		List<Travel> list = query.getResultList();
		em.close();
		return list;
	}
	//添加或修改交易信息
	@SuppressWarnings("unchecked")
	@Override
	public List<TravelTrade> saveTravelTrade() {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from travelTrade where travel_id != null";
		Query query = em.createNativeQuery(sql.toString());
		List<TravelTrade> list = query.getResultList();
		em.close();
		return list;
	}
}
