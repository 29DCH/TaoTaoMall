package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

public interface TokenService {

	TaotaoResult getUserByToken(String token);
}
