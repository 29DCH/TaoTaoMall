package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.service.SearchService;

/**
 * 查询服务
 * <p>Title: SearchServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月22日下午4:51:50
 * @version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String queryString, Long page) {
		//创建查询参数
		Map<String, String> param = new HashMap<>();
		param.put("kw", queryString);
		param.put("page", page + "");
		//调用taotao-search的服务进行搜索
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
		TaotaoResult result = TaotaoResult.format(json);
		if (result.getStatus() == 200) {
			TaotaoResult result2 = TaotaoResult.formatToPojo(json, SearchResult.class);
			SearchResult searchResult = (SearchResult) result2.getData();
			return searchResult;
		}
		
		return new SearchResult();
	}

}
