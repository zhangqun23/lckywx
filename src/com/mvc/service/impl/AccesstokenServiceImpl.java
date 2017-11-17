package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.AccesstokenDao;
import com.mvc.entity.AccessToken;
import com.mvc.service.AccesstokenService;

@Service("accesstokenServiceImpl")
public class AccesstokenServiceImpl implements  AccesstokenService {

	@Autowired
	AccesstokenDao accesstokenDao;

	@Override
	public List<AccessToken> getAccesstoken() {
		List<AccessToken> accessToken = accesstokenDao.getAccesstoken();
		return accessToken;
	}

	@Override
	public void updateAccesstoken(String jsapi_ticket, long now_timestamp, int id) {
		accesstokenDao.updateAccesstoken(jsapi_ticket, now_timestamp, id);
	}
	
}
