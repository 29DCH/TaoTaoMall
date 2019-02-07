package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;

import redis.clients.jedis.Jedis;

/**
 * 商品信息服务
 * <p>
 * Title: ItemServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 入云龙
 * @date 2015年8月24日上午9:38:13
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${REDIS_TAOTAO_ITEM_KEY}")
	private String REDIS_TAOTAO_ITEM_KEY;

	@Value("${REDIS_TAOTAO_ITME_EXPIRE}")
	private Integer REDIS_TAOTAO_ITME_EXPIRE;

	@Override
	public TbItem getItemById(long itemId) {
		// 先到redis中查询商品信息
		try {
			String json = jedisClient.get(REDIS_TAOTAO_ITEM_KEY + ":" + itemId);
			// 需要判断是否取到商品信息
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				// 转换失败，逃过缓存逻辑
				if (null == item) {
					throw new Exception("json数据转换失败");
				}
				return item;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 根据商品id查询商品信息。
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);

		// 把商品信息保存到redis中
		try {
			jedisClient.set(REDIS_TAOTAO_ITEM_KEY + ":" + itemId, JsonUtils.objectToJson(tbItem));
			jedisClient.expire(REDIS_TAOTAO_ITEM_KEY + ":" + itemId, REDIS_TAOTAO_ITME_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		// 从redis中取商品描述信息
		// 先到redis中查询商品信息
		try {
			String json = jedisClient.get(REDIS_TAOTAO_ITEM_KEY + ":DESC:" + itemId);
			// 需要判断是否取到商品信息
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				// 转换失败，逃过缓存逻辑
				if (null == itemDesc) {
					throw new Exception("json数据转换失败");
				}
				return itemDesc;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 从mysql中查询商品描述
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

		// 把商品信息保存到redis中
		try {
			jedisClient.set(REDIS_TAOTAO_ITEM_KEY + ":DESC:" + itemId, JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(REDIS_TAOTAO_ITEM_KEY + ":DESC:" + itemId, REDIS_TAOTAO_ITME_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;

	}

	@Override
	public TbItemParamItem getItemParamById(long itemId) {
		// 从redis中命中
		// 先到redis中查询商品信息
		try {
			String json = jedisClient.get(REDIS_TAOTAO_ITEM_KEY + ":PARAM:" + itemId);
			// 需要判断是否取到商品信息
			if (!StringUtils.isBlank(json)) {
				// 把json转换成java对象
				TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				// 转换失败，跳过缓存逻辑
				if (null == itemParamItem) {
					throw new Exception("json数据转换失败");
				}
				return itemParamItem;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 从数据库中查询规格参数
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && !list.isEmpty()) {
			TbItemParamItem itemParamItem = list.get(0);
			//此处把规格参数信息添加到redis中。
			// 把商品信息保存到redis中
			try {
				jedisClient.set(REDIS_TAOTAO_ITEM_KEY + ":PARAM:" + itemId, JsonUtils.objectToJson(itemParamItem));
				jedisClient.expire(REDIS_TAOTAO_ITEM_KEY + ":PARAM:" + itemId, REDIS_TAOTAO_ITME_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return itemParamItem;
		}
		return null;
	}

}
