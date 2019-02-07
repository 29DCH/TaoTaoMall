package com.taotao.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

/**
 * 商品搜索
 * <p>Title: SearchController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月22日下午5:00:54
 * @version 1.0
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search( @RequestParam("q") String queryString, 
			@RequestParam(defaultValue="1") Long page, Model model) throws Exception {
		//解决乱码问题
		queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
		SearchResult searchResult = searchService.search(queryString, page);
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", searchResult.getPage());
		
		return "search";
	}
}
