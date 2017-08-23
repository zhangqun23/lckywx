/**
 * @Title: TravelDao.java
 * @Package com.mvc.dao
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:56:15 
 */
package com.mvc.dao;

import java.util.List;

import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;

/**
 * @ClassName: TravelDao
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:56:15 
 */
public interface TravelDao {
	List<Travel> findTravelAlls0();//select
	List<Travel> findTravelAlls();//select by stime
	List<Travel> findTravelAlls1();//select by price
	List<TravelTrade> saveTravelTrade(TravelTrade travelTrade);//travel trade

}
