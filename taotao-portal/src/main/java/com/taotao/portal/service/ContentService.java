package com.taotao.portal.service;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface ContentService {

	List<TbContent> getContentList(long categoryId);
}
