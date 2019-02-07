package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.ItemCatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * 商品分类列表查询
 * <p>Title: ItemCatServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月18日上午11:47:15
 * @version 1.0
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public ItemCatResult getItemCatList() {
		
		ItemCatResult result = new ItemCatResult();
		result.setData(getList(0));
		return result;
	}
	
	/**
	 * 递归方法，根据parent查询一个树形列表
	 * <p>Title: getList</p>
	 * <p>Description: </p>
	 * @param parentId
	 * @return
	 */
	private List<?> getList(long parentId) {
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List resultList = new ArrayList<>();
		//循环计数
		int count = 0;
		for (TbItemCat tbItemCat : list) {
			//如果为父节点
			if (tbItemCat.getIsParent()) {
				ItemCatNode node = new ItemCatNode();
				node.setUrl("/products/" + tbItemCat.getId() + ".html");
				//判断是否为第一层节点
				if (parentId == 0) {
					node.setName("<a href='"+node.getUrl()+"'>"+tbItemCat.getName()+"</a>");
				} else {
					node.setName(tbItemCat.getName());
				}
				node.setItems(getList(tbItemCat.getId()));
				resultList.add(node);
			} else {
				String node = "/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName();
				resultList.add(node);
			}
			count++;
			//第一个层循环，只取14条记录
			if (parentId == 0 && count >= 14) {
				break;
			}
		}
		return resultList;
	}

}
