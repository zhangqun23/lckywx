package com.mvc.dao.impl;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.LoginDao;

@Repository("loginDaoImpl")
public class LoginDaoImpl implements LoginDao{
	
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@Override
	public String loginTest(String test) {
		
		// TODO Auto-generated method stub
		return null;
	}

}
