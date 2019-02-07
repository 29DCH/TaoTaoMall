package com.taotao.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	@Value("${REDIS_SESSION_EXPIRE}")
	private Integer REDIS_SESSION_EXPIRE;
	
	@Override
	public TaotaoResult getUserByToken(String token) {
		String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
		//没有取到用户信息
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(201, "Session已经过期");
		}
		//取到用户信息
		//把json数据转换成java对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		//更新session的过期时间
		jedisClient.expire(REDIS_SESSION_KEY + ":" + token, REDIS_SESSION_EXPIRE);
		
		return TaotaoResult.ok(user);
	}

}
