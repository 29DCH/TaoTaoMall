package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.service.CartService;

/**
 * 购物车服务
 * <p>
 * Title: CartServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 入云龙
 * @date 2015年8月27日上午9:32:21
 * @version 1.0
 */

@Service
public class CartServiceImpl implements CartService {

	@Value("${SERVICE_BASE_URL}")
	private String SERVICE_BASE_URL;
	@Value("${ITEM_BASE_URL}")
	private String ITEM_BASE_URL;
	@Value("${CAT_COOKIE_EXPIRE}")
	private Integer CAT_COOKIE_EXPIRE;

	@Override
	public TaotaoResult addCartItem(long itemId, Integer itemNum, HttpServletRequest request, HttpServletResponse response) {

		// 1、接收controller传递过来的参数：商品id
		//从cookie中取购物车商品列表
		List<Item> list = getItemListFromCart(request, response);
		boolean falg = false;
		
		for (Item item : list) {
			if (item.getId() == itemId) {
				falg = true;
				item.setCartItemNum(item.getCartItemNum() + itemNum);
				break;
			}
		}
		//判断是否存在此商品
		if (!falg) {
			// 2、根据商品id查询商品信息
			String json = HttpClientUtil.doGet(SERVICE_BASE_URL + ITEM_BASE_URL + itemId);
			// 把json转换成java对象
			TaotaoResult result = TaotaoResult.formatToPojo(json, Item.class);
			Item item = null;
			if (result != null && result.getStatus() == 200) {
				// 取商品信息
				item = (Item) result.getData();
				
			}
			// 3、把商品信息放入list
			list.add(item);
			// 设置数量
			item.setCartItemNum(itemNum);
		}
		// 4、把list序列号写入cookie，进行编码。
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), CAT_COOKIE_EXPIRE,
				"utf-8");

		return TaotaoResult.ok();
	}
	
	/**
	 * 取购物车信息
	 */
	public List<Item> getItemListFromCart(HttpServletRequest request, HttpServletResponse response) {
		//从cookie中取商品列表
		String string = CookieUtils.getCookieValue(request, "TT_CART", "utf-8");
		try {
			List<Item> list = JsonUtils.jsonToList(string, Item.class);
			if (list == null) {
				return new ArrayList<Item>();
			}
			return list;
		} catch (Exception e) {
			return new ArrayList<Item>();
		}
	}

	/**
	 * 取购物车商品列表
	 * <p>Title: getCatItemList</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 * @see com.taotao.portal.service.CartService#getCatItemList(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<Item> getCatItemList(HttpServletRequest request) {
		//从cookie中取商品列表
		List<Item> list = getItemListFromCart(request, null);
		return list;
	}

	/**
	 * 更新购物车商品数量
	 * <p>Title: updateItemNum</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @param itemNum
	 * @return
	 * @see com.taotao.portal.service.CartService#updateItemNum(long, int)
	 */
	@Override
	public TaotaoResult updateItemNum(long itemId, int itemNum, 
			HttpServletRequest request, HttpServletResponse response) {
		//从cookie中把商品列表取出来
		List<Item> list = getItemListFromCart(request, response);
		//遍历列表找商品
		for (Item item : list) {
			if (item.getId().longValue() == itemId) {
				//更新商品数量
				item.setCartItemNum(itemNum);
				break;
			}
		}
		//把购物车商品列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), CAT_COOKIE_EXPIRE,
				"utf-8");

		return TaotaoResult.ok();
	}

}
