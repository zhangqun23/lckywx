package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entity.TravelTrade;

public interface TravelTradeRepository extends JpaRepository<TravelTrade, Integer>{
	//根据trtr_id查找旅游交易信息
	@Query("select tt from TravelTrade tt where is_state = 1 and trtr_id = :trtr_id")
	public TravelTrade selectTrTrInfoById(@Param("trtr_id") Integer trtr_id);

	//根据trtr_id更新退款交易
	@Modifying
	@Query("update TravelTrade set refund_id = :refund_id , refund_fee = :refund_fee , refund_time = :refund_time , is_state = 2 where trtr_id = :trtr_id ")
	public void updateRefundTrade(@Param("refund_id") String refund_id, @Param("refund_fee") Integer refund_fee,
			@Param("refund_time") String data, @Param("trtr_id") Integer trtr_id);
	@Modifying
	@Query("update TravelTrade set refund_fee = :refund_fee , refund_time = :refund_time , is_state = 2 where trtr_id = :trtr_id ")
	public void updateRefundTrade(@Param("refund_fee") Integer refund_fee,
			@Param("refund_time") String data, @Param("trtr_id") Integer trtr_id);
}

