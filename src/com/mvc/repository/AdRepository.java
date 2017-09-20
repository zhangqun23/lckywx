package com.mvc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mvc.entiy.Ad;

public interface AdRepository extends JpaRepository<Ad, Integer>{
	//返回相应类型广告
	@Query("select t from Ad t where ad_type = :ad_type and is_delete=0 and ad_state=1 order by ad_stime desc")
	public List<Ad> findAdByType(@Param("ad_type") Integer adType);
	//根据id返回广告
	@Query("select t from Ad t where ad_id = :adId and is_delete=0 ")
	public Ad findAdById(@Param("adId") int adid);
	//根据openId,adState查找广告
	@Query("select o from Ad o where ad_state = :ad_state and open_id = :open_id and is_delete=0 order by ad_stime desc")
	public List<Ad> findMyPlaceAd( @Param("ad_state") Integer adState, @Param("open_id") String openId);
}
