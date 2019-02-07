package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCatService;

@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<TreeNode> getContentCatList(long parentId) {
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<TreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			TreeNode node = new TreeNode(tbContentCategory.getId(), tbContentCategory.getName(),
					tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		
		return resultList;
	}
	
	@Override
	public TaotaoResult createNode(long parentId, String name) {
		//创建一内容分类pojo
		TbContentCategory node = new TbContentCategory();
		node.setName(name);
		node.setIsParent(false);
		node.setParentId(parentId);
		//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		node.setSortOrder(1);
		//状态。可选值:1(正常),2(删除)
		node.setStatus(1);
		node.setUpdated(new Date());
		node.setCreated(new Date());
		//插入节点数据
		contentCategoryMapper.insert(node);
		//更新父节点
		//取父节点
		/*TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(parentId);*/
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			//更新父节点isParent列
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		//返回结果
		return TaotaoResult.ok(node);
	}

}
