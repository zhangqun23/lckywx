/**
 * @Title: TravelTradeRepository.java
 * @Package com.mvc.repository
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 下午3:35:51 
 */
package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.entiy.TravelTrade;

/**
 * @ClassName: TravelTradeRepository
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 下午3:35:51 
 * 
 *
 */
public interface TravelTradeRepository extends JpaRepository<TravelTrade, Integer>{

}
