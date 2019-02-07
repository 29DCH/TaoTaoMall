package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

public interface RedisSyncService {

	TaotaoResult syncContent(String key);
}
