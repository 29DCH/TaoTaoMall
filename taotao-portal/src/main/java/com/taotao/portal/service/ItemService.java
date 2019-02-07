package com.taotao.portal.service;

import com.taotao.portal.pojo.Item;

public interface ItemService {

	Item getItemById(long itemId);
	String getItemDesc(long itemId);
	String getItemParam(long itemId);
}
