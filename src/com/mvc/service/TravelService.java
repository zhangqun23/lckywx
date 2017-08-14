/**
 * @Title: TravelService.java
 * @Package com.mvc.service
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:49:16 
 */
package com.mvc.service;

import java.util.List;

import com.mvc.entiy.Travel;

/**
 * @ClassName: TravelService
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:49:16 
 * 
 *
 */
public interface TravelService {

	List<Travel> findTravelAlls(String useDate);

}
