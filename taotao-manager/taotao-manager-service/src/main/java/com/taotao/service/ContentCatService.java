package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeNode;

public interface ContentCatService {

	List<TreeNode> getContentCatList(long parentId);
	TaotaoResult createNode(long parentId, String name);
}
