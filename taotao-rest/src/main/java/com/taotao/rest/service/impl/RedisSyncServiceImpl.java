package com.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisSyncService;

/**
 * 同步缓存方法
 * <p>Title: RedisSyncServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月21日下午3:56:46
 * @version 1.0
 */
@Service
public class RedisSyncServiceImpl implements RedisSyncService {

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	@Override
	public TaotaoResult syncContent(String key) {
		jedisClient.hdel(REDIS_CONTENT_KEY, key);
		
		return TaotaoResult.ok();
	}

}
