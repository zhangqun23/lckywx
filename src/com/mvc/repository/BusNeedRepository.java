package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entiy.BusNeed;

/**
 * 班车
 * @author wanghuimin
 * @date 2017年8月9日
 */
public interface BusNeedRepository extends JpaRepository<BusNeed, Integer> {
	// 根据使用时间查询班车定制需求
	@Query("select b from BusNeed b where bune_gath_time = :bune_gath_time and is_delete=0 ")
	public List<BusNeed> findByUsertime(@Param("bune_gath_time") String useDate);

}
