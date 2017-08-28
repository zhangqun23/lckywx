package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.entiy.User;
/**
 * 用户
 * @author wanghuimin
 * @date 2017年8月25日
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
