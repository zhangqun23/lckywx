package com.mvc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mvc.entiy.Ad;

public interface AdRepository extends JpaRepository<Ad, Integer>{
	//类型为空返回全部广告
	@Query("select a from Ad a where is_delete=1 and ad_state=1 order by ad_stime desc")
	public List<Ad> findAlls();
	//返回相应类型广告
	@Query("select t from Ad t where ad_type = :ad_type and is_delete=1 and ad_state=1 order by ad_stime desc")
	public List<Ad> findAdByType(@Param("ad_type") Integer adType);
	//根据id返回广告
	@Query("select t from Ad t where ad_id = :adId and is_delete=1 ")
	public Ad findAdById(@Param("adId") int adid);
	//根据openId,adType,adState查找广告
	@Query("select o from Ad o where ad_type = :ad_type and ad_state = :ad_state and open_id = :open_id and is_delete=1 order by ad_stime desc")
	public List<Ad> findMyPlaceAd(@Param("ad_type") Integer adType, @Param("ad_state") Integer adState, @Param("open_id") String openId);
	//根据openId查找广告
	@Query("select o from Ad o where open_id = :open_id and is_delete=1 order by ad_stime desc")
	public List<Ad> findMyPlaceAdAll(@Param("open_id")String openId);
}
