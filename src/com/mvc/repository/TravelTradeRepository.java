package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entiy.TravelTrade;

public interface TravelTradeRepository extends JpaRepository<TravelTrade, Integer>{
	//根据trtr_id查找旅游交易信息
		@Query("select tt from TravelTrade tt where is_state = 1 and trtr_id = :trtr_id")
		public TravelTrade selectTrTrInfoById(@Param("trtr_id") Integer trtr_id);
}

