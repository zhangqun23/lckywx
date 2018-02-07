package com.mvc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entity.SmallGoods;

/**
 * 小件货运
 * @author lijing
 * @date 2017年8月11日
 */
public interface SmallGoodsRepository extends JpaRepository<SmallGoods, Integer>{
	
	@Query("select s from SmallGoods s where smgo_deal_time between :startDate and :endDate and openid = :openid and is_delete=1 ")
	public List<SmallGoods> findByTimeAndPlace(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("openid") String openid);
	
	@Query("select s from SmallGoods s where openid = :openid and is_delete=1 ")
	public List<SmallGoods> findAllSmallGoods(@Param("openid") String openid);
	
	@Query("select s from SmallGoods s where smgo_id = :sgid and is_delete=1 ")
	public SmallGoods findSmallGoodsById(@Param("sgid") Integer sgid);
}
