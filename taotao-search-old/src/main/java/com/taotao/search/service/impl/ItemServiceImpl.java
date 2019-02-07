package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SolrItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.service.ItemService;

/**
 * 向索引库中导入商品信息
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月22日上午11:42:32
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public TaotaoResult importAllItems() throws Exception {
		//查询商品列表
		List<SolrItem> list = itemMapper.getItemList();
		//向索引库中添加文档
		for (SolrItem solrItem : list) {
			//创建文档对象
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", solrItem.getId());
			document.setField("item_title", solrItem.getTitle());
			document.setField("item_sell_point", solrItem.getSell_point());
			document.setField("item_price", solrItem.getPrice());
			document.setField("item_image", solrItem.getImage());
			document.setField("item_category_name", solrItem.getItem_cat_name());
			document.setField("item_desc", solrItem.getItem_desc());
			//向索引库中添加文档
			solrServer.add(document);
		}
		//提交修改
		solrServer.commit();
		
		return TaotaoResult.ok();
	}

}
