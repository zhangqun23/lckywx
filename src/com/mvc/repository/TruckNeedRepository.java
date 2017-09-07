package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.entiy.Truck_need;

/**
 * 货主信息录入
 * @author ghl
 * @date   2017年9月6日
 */
public interface TruckNeedRepository extends JpaRepository<Truck_need, Integer> {

}
