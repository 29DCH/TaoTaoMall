package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.service.ContentCatService;

/**
 * 内容分类管理
 * <p>Title: ContentCatController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月19日上午9:57:46
 * @version 1.0
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatController {

	@Autowired
	private ContentCatService contentCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<TreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<TreeNode> list = contentCatService.getContentCatList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createNode(Long parentId, String name) {
		TaotaoResult result = contentCatService.createNode(parentId, name);
		return result;
	}
}
