package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mvc.entiy.BusTrade;

/**
 * 班车
 * @author wanghuimin
 * @date 2017年8月9日
 */
public interface BusTradeRepository extends JpaRepository<BusTrade, Integer>  {

}
