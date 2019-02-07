package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.service.ItemService;

/**
 * 商品信息管理
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月24日上午10:51:05
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${SERVICE_BASE_URL}")
	private String SERVICE_BASE_URL;
	@Value("${ITEM_BASE_URL}")
	private String ITEM_BASE_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
	
	@Override
	public Item getItemById(long itemId) {
		
		//调用taotao-rest服务
		String jsonResult = HttpClientUtil.doGet(SERVICE_BASE_URL + ITEM_BASE_URL + itemId);
		//转换成taotaoresult
		TaotaoResult result = TaotaoResult.formatToPojo(jsonResult, Item.class);
		if (null != result && result.getStatus() == 200) {
			Item item = (Item)result.getData();
			return item;
		}
		return null;
	}

	/**
	 * 取商品描述
	 * <p>Title: getItemDesc</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @return
	 * @see com.taotao.portal.service.ItemService#getItemDesc(long)
	 */
	@Override
	public String getItemDesc(long itemId) {
		
		String jsonData = HttpClientUtil.doGet(SERVICE_BASE_URL + ITEM_DESC_URL + itemId);
		//把json转换成java对象
		TaotaoResult result = TaotaoResult.formatToPojo(jsonData, TbItemDesc.class);
		if (null != result && result.getStatus() == 200) {
			TbItemDesc desc = (TbItemDesc) result.getData();
			//取商品描述
			String descString = desc.getItemDesc();
			return descString;
		}
		
		return "";
	}

	@Override
	public String getItemParam(long itemId) {
		//调用taotao-rest服务，请求规格参数数据
		String jsonData = HttpClientUtil.doGet(SERVICE_BASE_URL + ITEM_PARAM_URL + itemId);
		//转换成taotaoResult对象
		TaotaoResult result = TaotaoResult.formatToPojo(jsonData, TbItemParamItem.class);
		//请求出错，返回空串
		if (null == result || result.getStatus() != 200) {
			return "";
		}
		TbItemParamItem itemParamItem = (TbItemParamItem) result.getData();
		//取规格参数
		String paramData = itemParamItem.getParamData();
		//把规格参数json格式的数据转换成java对象
		List<Map> list = JsonUtils.jsonToList(paramData, Map.class);
		//根据list生成html
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("     <tbody>\n");
		for(Map param:list) {
			sb.append("          <tr>\n");
			sb.append("               <th class=\"tdTitle\" colspan=\"2\">"+param.get("group")+"</th>\n");
			sb.append("          </tr>\n");
			//取规格项
			List<Map> object = (List<Map>) param.get("params");
			for (Map map : object) {
				sb.append("          <tr>\n");
				sb.append("               <td class=\"tdTitle\">"+map.get("k")+"</td>\n");
				sb.append("               <td>"+map.get("v")+"</td>\n");
				sb.append("          </tr>\n");
			}
		}
		sb.append("     </tbody>\n");
		sb.append("</table>");
		return sb.toString();
	}

}
