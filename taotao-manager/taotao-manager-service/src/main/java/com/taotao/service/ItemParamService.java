package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {

	TaotaoResult checkParam(long cid);
	TaotaoResult addItemParam(long cid, String template);
	TaotaoResult getItemParemByCid(long cid);
}
