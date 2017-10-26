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
public class TravelTradeDaoImpl implements TravelTradeDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@SuppressWarnings("unchecked")
	@Override
	public List<TravelTrade> selectMyTravelInfoByOId(String openid, Integer offset, Integer limit, String state) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from travel_trade tt where tt.is_state = '" + state + "' ");
		sql.append(" and tt.open_id = '" + openid + "' limit :offset, :end");

		Query query = em.createNativeQuery(sql.toString(), TravelTrade.class);
		query.setParameter("offset", offset);
		query.setParameter("end", limit);
		List<TravelTrade> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public void addTravelTrade(TravelTrade travelTrade) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into travel_trade (is_state, open_id, refund_fee, refund_id, refund_time, trade_discount, ");
		sql.append(
				"trade_time, transaction_id, travel, trtr_cnum, trtr_mnum, trtr_num, trtr_price, trtr_tel ) values ");
		sql.append("( " + travelTrade.getIs_state() + ", '" + travelTrade.getOpen_id() + "', "
				+ travelTrade.getRefund_fee() + ", "+travelTrade.getRefund_id()+", '"+travelTrade.getRefund_time()+"', "+travelTrade.getTrade_discount()+", ");
		sql.append(
				" '"+travelTrade.getTrade_time()+"', '"+travelTrade.getTransaction_id()+"', "+travelTrade.getTravel().getTravel_id()+","+travelTrade.getTrtr_cnum()+","+travelTrade.getTrtr_mnum()+", "+travelTrade.getTrtr_num()+", "+ travelTrade.getTrtr_price()+", "+travelTrade.getTrtr_tel()+") ; ");
		Query query = em.createNativeQuery(sql.toString());
		query.executeUpdate();
		em.flush();
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void saveTradeState(String trade_num, String transaction_id , String datanow) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into travel_trade ( trade_time,  trtr_num , transaction_id ) values ");
		sql.append("( '" + datanow + "', '" + trade_num + "', " + transaction_id + "); ");
		Query query = em.createNativeQuery(sql.toString());
		query.executeUpdate();
		em.flush();
		em.getTransaction().commit();
		em.close();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getTravelTrade(String out_trade_no) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append("select trtr_id from travel_trade where trtr_num = '" + out_trade_no + "' ");

		Query query = em.createNativeQuery(sql.toString());
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}

	@Override
	public void updateTravelTrade(TravelTrade travelTrade) {
		StringBuilder selectSql = new StringBuilder();
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			selectSql.append("update travel_trade set is_state = " + travelTrade.getIs_state() + ", open_id = '" + travelTrade.getOpen_id() + "' , trade_discount = '");
			selectSql.append(travelTrade.getTrade_discount() + "' ,travel = '"+travelTrade.getTravel().getTravel_id() +"', trtr_cnum = '" + travelTrade.getTrtr_cnum());
			selectSql.append("' , trtr_mnum = '" +travelTrade.getTrtr_mnum()+ "', trtr_price = '" + travelTrade.getTrtr_price() + "' ,trtr_tel ='"+travelTrade.getTrtr_tel()+"'");
			selectSql.append(" where trtr_num = '" + travelTrade.getTrtr_num() + "' ");
			Query query = em.createNativeQuery(selectSql.toString());
			query.executeUpdate();
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

}
