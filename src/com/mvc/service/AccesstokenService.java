package com.mvc.service;

import java.util.List;

import com.mvc.entity.AccessToken;


public interface AccesstokenService {

	List<AccessToken> getAccesstoken();

	void updateAccesstoken(String jsapi_ticket, long now_timestamp, int id);
}
