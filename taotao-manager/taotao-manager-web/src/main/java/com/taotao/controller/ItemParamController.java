package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ItemParamService;

/**
 * 规格参数模板Controller
 * <p>Title: ItemParamController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月16日上午10:51:19
 * @version 1.0
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	public TaotaoResult checkItemParam(@PathVariable Long cid) {
		TaotaoResult result = itemParamService.checkParam(cid);
		return result;
	}
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult addItemParam(@PathVariable Long cid, String paramData) {
		TaotaoResult result = itemParamService.addItemParam(cid, paramData);
		return result;
	}
	
	@RequestMapping("/cid/{cid}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long cid) {
		TaotaoResult result = itemParamService.getItemParemByCid(cid);
		return result;
	}
	
}
