package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.TravelTradeDao;
import com.mvc.entity.Travel;
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
		sql.append("( " + travelTrade.getIs_state() + ", " + travelTrade.getOpen_id() + ", "
				+ travelTrade.getRefund_fee() + ", "+travelTrade.getRefund_id()+", "+travelTrade.getRefund_time()+", "+travelTrade.getTrade_discount()+", ");
		sql.append(
				" "+travelTrade.getTrade_time()+", "+travelTrade.getTransaction_id()+", "+travelTrade.getTravel().getTravel_id()+","+travelTrade.getTrtr_cnum()+","+travelTrade.getTrtr_mnum()+", "+travelTrade.getTrtr_num()+", "+ travelTrade.getTrtr_price()+", "+travelTrade.getTrtr_tel()+") ; ");
		Query query = em.createNativeQuery(sql.toString());
		query.executeUpdate();
		em.flush();
		em.getTransaction().commit();
		em.close();
	}

}
