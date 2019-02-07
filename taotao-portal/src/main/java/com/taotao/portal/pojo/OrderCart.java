package com.taotao.portal.pojo;

import java.util.List;

public class OrderCart {
	
	//商品列表
	private List<Item> itemList;
	//用户的配送地址列表(为实现功能)
	private List<Object> shippingList;
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public List<Object> getShippingList() {
		return shippingList;
	}
	public void setShippingList(List<Object> shippingList) {
		this.shippingList = shippingList;
	}
	
	

}
