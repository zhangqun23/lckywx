package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entiy.Travel;

public interface TravelRepository  extends JpaRepository<Travel, Integer>{
	//根据id查找旅游信息
	@Query("select t from Travel t where is_delete=1 and travle_id = :travelid")
	public Travel findTravelById(@Param("travelid") Integer travelid);
}