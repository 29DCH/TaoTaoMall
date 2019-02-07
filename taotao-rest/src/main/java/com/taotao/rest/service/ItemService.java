package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

public interface ItemService {

	TbItem getItemById(long itemId) ;
	TbItemDesc getItemDescById(long itemId);
	TbItemParamItem getItemParamById(long itemId);
}
