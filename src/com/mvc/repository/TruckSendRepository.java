package com.mvc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entiy.Truck_send;

public interface TruckSendRepository extends JpaRepository<Truck_send, Integer> {
	//根据truckSendId查询truckSend详情
	@Query("select t from Truck_send t where trse_id = :trse_id  ")
	Truck_send findTruckSendInfo(@Param("trse_id") Integer trseId);

}
