package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.Item;

public interface CartService {

	TaotaoResult addCartItem(long itemId, Integer itemNum, HttpServletRequest request, HttpServletResponse response);
	List<Item> getCatItemList(HttpServletRequest request);
	TaotaoResult updateItemNum(long itemId, int itemNum, HttpServletRequest request, HttpServletResponse response);
	
}
