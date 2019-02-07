package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.rest.service.RedisSyncService;

/**
 * 同步缓存服务
 * <p>Title: RedisSyncController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月21日下午4:01:29
 * @version 1.0
 */
@Controller
@RequestMapping("/sync")
public class RedisSyncController {

	@Autowired
	private RedisSyncService redisSyncService;
	
	@RequestMapping("/content/{categoryId}")
	@ResponseBody
	public TaotaoResult syncContent(@PathVariable String categoryId) {
		
		try {
			redisSyncService.syncContent(categoryId);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}
	
}
