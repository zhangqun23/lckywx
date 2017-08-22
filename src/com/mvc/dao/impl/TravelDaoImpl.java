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
 */
@Repository("travelDaoImpl")
public class TravelDaoImpl implements TravelDao{
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	//按时间查询旅游信息
	@SuppressWarnings("unchecked")
	@Override
	public List<Travel> findTravelAlls() {
		EntityManager em = emf.createEntityManager();
		SimpleDateFormat getDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String getdate = getDate.format(new Date());
		//判断时间
<<<<<<< HEAD
		String sql = "select * from travel where ( travel_stime between '"+ getdate +"' and '2525-01-01 01:00:00') and is_delete = 0 and travel_left_num > 0 order by travel_stime asc";
		Query query = em.createNativeQuery(sql.toString(),Travel.class);//对象和表对应
=======

		String sql = "select * from travel where travel_stime  between '"+ getDate +"' and '1899-01-01 00:00:00' and is_delete = 0 and travel_left_num > 0 order by travel_stime asc";
		Query query = em.createNativeQuery(sql.toString());

>>>>>>> 1bbdaf17166cc02f3721e8fbcacbce3d2dd96154
		List<Travel> list = query.getResultList();
		em.close();
		return list;
		}
	 //按成人票价格查询旅游信息
	@SuppressWarnings("unchecked")
	public List<Travel> findTravelAlls1() {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from travel where is_delete=0 and travel_left_num > 0 order by travel_mprice asc";
		Query query = em.createNativeQuery(sql.toString());
		List<Travel> list = query.getResultList();
		em.close();
		return list;
	}
	//添加或修改交易信息
	@SuppressWarnings("unchecked")
	public List<TravelTrade> saveTravelTrade(TravelTrade travelTrade) {
		EntityManager em = emf.createEntityManager();
		String sql = "select * from travel_trade where travel_id is not null";
		Query query = em.createNativeQuery(sql.toString());
		List<TravelTrade> list = query.getResultList();
		em.close();
		return list;
	}
}
