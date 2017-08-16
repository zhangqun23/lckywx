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
	//根据旅游时间查询旅游信息
	@Query("select t from Travel t where travel_stime = :travel_stime and is_delete=0 ")
	public List<Travel> findByUsertime(@Param("travel_stime") String useDate);
	//travel_stime = useDate参数;
	@Query("select p from Travel p where travel_mprice = :travel_mprice and is_delete=0 ")
	public List<Travel> findByUserprice(@Param("travel_mprice") String usePrice);

}
