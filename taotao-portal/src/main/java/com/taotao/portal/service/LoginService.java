package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

public interface LoginService {

	TbUser getUserByToken(String token);
}
