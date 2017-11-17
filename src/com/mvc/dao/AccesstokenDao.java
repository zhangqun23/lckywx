package com.mvc.dao;

import java.util.List;

import com.mvc.entity.AccessToken;

public interface AccesstokenDao {

	List<AccessToken> getAccesstoken();

	void updateAccesstoken(String jsapi_ticket, long now_timestamp, int id);

}
