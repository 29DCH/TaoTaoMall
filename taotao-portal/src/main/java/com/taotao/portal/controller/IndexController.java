package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;

/**
 * 展示首页controller
 * <p>Title: IndexController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月18日上午10:25:00
 * @version 1.0
 */
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		//首页大广告为categoryId固定为89。
		List<TbContent> list = contentService.getContentList(89);
		List<Map> resultList = new ArrayList<>();
		for (TbContent tbContent : list) {
			Map item = new HashMap<>();
			item.put("src", tbContent.getPic());
			item.put("width", 670);
			item.put("height", 240);
			item.put("srcB", tbContent.getPic2());
			item.put("widthB", 550);
			item.put("heightB", 240);
			item.put("href", tbContent.getUrl());
			item.put("alt", tbContent.getTitle());
			resultList.add(item);
		}
		String json = JsonUtils.objectToJson(resultList);
		model.addAttribute("ad1", json);
		return "index";
	}
}
