/**
 * @Title: TravelDaoImpl.java
 * @Package com.mvc.dao.impl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:56:56 
 */
package com.mvc.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.TravelDao;
import com.mvc.entity.Travel;

/**
 * @ClassName: TravelDaoImpl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:56:56 
 */
@Repository("travelDaoImpl")
public class TravelDaoImpl implements TravelDao{
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	//直接查询
	@SuppressWarnings("unchecked")
	@Override
	public List<Travel> findTravelAlls(Integer offset, Integer limit) {	
		
		SimpleDateFormat getDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String getdate = getDate.format(new Date());
		
		EntityManager em = emf.createEntityManager();
		String sql = "select * from travel where is_delete=0 and travel_stime > '"+ getdate +"' order by travel_stime asc limit :offset, :end";
		Query query = em.createNativeQuery(sql.toString(),Travel.class);//对象和表对应
		query.setParameter("offset", offset);
		query.setParameter("end", limit);
		List<Travel> list = query.getResultList();
		em.close();
		return list;
		}
	//按时间查询旅游信息
	@SuppressWarnings("unchecked")
	@Override
	public List<Travel> findTravelAlls(Map<String, Object> map) {
		String startTime = null;
		if ((String) map.get("travel_stime")!=null) {
			startTime = (String) map.get("travel_stime");//开始时间
		}
		EntityManager em = emf.createEntityManager();
		SimpleDateFormat getDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String getdate = getDate.format(new Date());
		//判断时间
		String sql = "select * from travel where ( travel_stime between '"+ getdate +"' and '2525-01-01 01:00:00') and is_delete = 0 and travel_left_num > 0 order by travel_stime asc";
		Query query = em.createNativeQuery(sql.toString(),Travel.class);//对象和表对应
		List<Travel> list = query.getResultList();
		em.close();
		return list;
		}
	 //按成人票价格查询旅游信息
	@SuppressWarnings("unchecked")
	@Override
	public List<Travel> findTravelAlls1(Map<String, Object> map) {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from travel where is_delete=0 and travel_left_num > 0 order by travel_mprice asc";
		Query query = em.createNativeQuery(sql.toString(),Travel.class);
		List<Travel> list = query.getResultList();
		em.close();
		return list;
	}
	
	//根据openid查找旅游信息
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> selectMyTravelInfoByOId(String openid, Integer offset, Integer limit, String state) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from travel t left join travel_trade tt on tt.travel = t.travel_id where tt.is_state = '" + state + "' ");
		sql.append(" and tt.open_id = '" +openid+ "' limit :offset, :end");
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("offset", offset);
		query.setParameter("end", limit);
		List<Object> list =  query.getResultList();
		em.close();
		return list;
	}
	
//	//根据trtr_id查找旅游交易信息
//	@Override
//	public List<TravelTrade> selectTrTrInfoById(String trtr_id) {
//		EntityManager em = emf.createEntityManager();
//		StringBuilder sql = new StringBuilder();
//		sql.append("select * from travel_trade where is_state = 1 and trtr_id = '" + trtr_id + "' ");
//		
//		Query query = em.createNativeQuery(sql.toString(),TravelTrade.class);
//		List<TravelTrade> list = query.getResultList();
//		em.close();
//		return list;
//	}
}
