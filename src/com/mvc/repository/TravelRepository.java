package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.entity.Travel;
import com.mvc.entity.TravelTrade;

public interface TravelRepository  extends JpaRepository<Travel, Integer>{
	//根据id查找旅游信息
	@Query("select t from Travel t where is_delete=0 and travel_id = :travelid")
	public Travel findTravelById(@Param("travelid") Integer travelid);
	//更新剩余票数
	@Modifying
	@Query("update Travel set travel_left_num = :leftnum where travel_id = :travelid and is_delete=0 ")
	public void updateTravel(@Param("leftnum")Integer left_num,@Param("travelid") Integer travel_id);

}