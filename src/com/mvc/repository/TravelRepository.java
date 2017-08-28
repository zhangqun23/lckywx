/**
 * @Title: Travel.java
 * @Package com.mvc.repository
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:38:32 
 */
package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entiy.BusNeed;
import com.mvc.entiy.Travel;



/**
 * @ClassName: Travel
 * @Description: 查询旅游信息
 * @author ycj
 * @date 2017年8月14日 上午11:38:32 
 * 
 *
 */
public interface TravelRepository extends JpaRepository<Travel, Integer>{
		@Query("select t from Travel t ")
		public List<Travel> findTravelAlls0();
}
