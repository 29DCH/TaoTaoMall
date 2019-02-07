package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.service.ItemParamItemService;

@Controller
public class ItemParamItemController {

	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/showParam/{itemId}")
	public String showParam(@PathVariable Long itemId, Model model) {
		String string = itemParamItemService.getItemParemById(itemId);
		model.addAttribute("html", string);
		return "item-param";
	}
	
}
