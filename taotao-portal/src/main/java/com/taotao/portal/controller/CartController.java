package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.service.CartService;

/**
 * 购物车管理
 * <p>Title: CartController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月27日上午10:52:53
 * @version 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	/**
	 * 添加购物车商品
	 * <p>Title: addCartItem</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @param itemNum
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, Integer itemNum,
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = cartService.addCartItem(itemId, itemNum, request, response);
		if (result.getStatus() == 200) {
			return "cart-success";
		}
		return "error/exception";
	}
	
	/**
	 * 展示购物车商品
	 * <p>Title: showCart</p>
	 * <p>Description: </p>
	 * @param model
	 * @return
	 */
	@RequestMapping("/cart")
	public String showCart(Model model, HttpServletRequest request) {
		List<Item> list = cartService.getCatItemList(request);
		model.addAttribute("cartList", list);
		return "cart";
	}
	@RequestMapping("/update/num/{itemId}/{itemNum}")
	@ResponseBody
	public TaotaoResult updateItemNum(@PathVariable Long itemId, @PathVariable Integer itemNum,
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = cartService.updateItemNum(itemId, itemNum, request, response);
		return result;
	}
}
