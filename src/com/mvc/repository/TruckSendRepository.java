package com.mvc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entiy.TruckSend;

public interface TruckSendRepository extends JpaRepository<TruckSend, Integer> {	
	//根据truckSendId查询truckSend详情
	@Query("select t from TruckSend t where trse_id = :trse_id  ")
	TruckSend findTruckSendInfo(@Param("trse_id") Integer trseId);
}
