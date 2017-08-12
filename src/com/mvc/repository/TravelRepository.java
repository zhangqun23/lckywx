package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entiy.Travel;
/**
 * 
 * @ClassName: TravelRepository
 * @Description: 旅游travel
 * @author ycj
 * @date 2017年8月12日 上午9:52:13 
 * 
 *
 */
public interface TravelRepository extends JpaRepository<Travel, Integer> {
	// 根据使用时间查询旅游信息
		@Query("select b from Travel b where travel_stime  = :travel_stime  and is_delete=0 ")
		public List<Travel> findByUsertime(@Param("travel_stime ") String useDate);

}
