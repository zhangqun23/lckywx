/**
 * @Title: Travel.java
 * @Package com.mvc.repository
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:38:32 
 */
package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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


}
