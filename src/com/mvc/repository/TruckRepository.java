package com.mvc.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mvc.entiy.Truck;

public interface TruckRepository extends JpaRepository<Truck, Integer> {
	@Query("select t from Truck t where open_id = :openid ")
	public Truck findTruck(@Param("openid") String openId);
	@Query("select t from Truck t where trck_id = :trckId ")
	public Truck findTruck(@Param("trckId") Integer trckId);
}
