package com.taotao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/q")
	@ResponseBody
	public TaotaoResult search(@RequestParam("kw")String queryString, @RequestParam(defaultValue="1")Long page, @RequestParam(defaultValue="60")Long pageSize) {
		if (StringUtils.isBlank(queryString)) {
			return TaotaoResult.build(400, "查询条件不能为空");
		}
		try {
			//解决get乱码问题
			queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
			SearchResult searchResult = searchService.search(queryString, page, pageSize);
			return TaotaoResult.ok(searchResult);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
}
