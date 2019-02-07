package com.taotao.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * 商品分类服务
 * <p>Title: ItemCatController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月18日下午2:35:32
 * @version 1.0
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	/*@RequestMapping("/list")
	@ResponseBody
	public Object getItemCatList(String callback) {
		ItemCatResult result = itemCatService.getItemCatList();
		//判断是否是jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		}
		//jsonp包装
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
		mappingJacksonValue.setJsonpFunction(callback);
		
		return mappingJacksonValue;
	}*/
	//jsonp第二种方法
	@RequestMapping(value="/list", produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback) {
		ItemCatResult result = itemCatService.getItemCatList();
		//判断是否是jsonp调用
		if (StringUtils.isBlank(callback)) {
			return JsonUtils.objectToJson(result);
		}
		
		return callback + "(" + JsonUtils.objectToJson(result) + ");";
	}
}
