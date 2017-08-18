package com.mvc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mvc.entiy.Ad;

public interface AdRepository extends JpaRepository<Ad, Integer>{
	//类型为空返回全部广告
	@Query("select a from Ad a where is_delete=1 ")
	public List<Ad> findAlls();
	//返回相应类型广告
	@Query("select t from Ad t where ad_type = :ad_type and is_delete=1 ")
	public List<Ad> findAdByType(@Param("ad_type") String adType);
}
