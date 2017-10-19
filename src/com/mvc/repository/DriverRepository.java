package com.mvc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entity.Driver;

/**
 * 零担货运
 * @author ghl
 * @date   2017年9月6日
 */

public interface DriverRepository extends JpaRepository<Driver, Integer> {
	@Query("select t from Driver t where driver_id = :driverId ")	
	Driver findDriver(@Param("driverId")Integer driverId);

}
