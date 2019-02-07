package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ContentService;

/**
 * 内容管理服务 
 * <p>Title: ContentController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月19日上午11:40:55
 * @version 1.0
 */
@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/category/{categoryId}")
	@ResponseBody
	public TaotaoResult categoryList(@PathVariable Long categoryId) {
		TaotaoResult result = contentService.getContentList(categoryId);
		return result;
	}
}
