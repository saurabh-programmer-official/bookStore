package com.fabellus.rabbitmq;

import java.io.Serializable;
import java.util.List;

import com.fabellus.placeorder.Order;
import com.fabellus.placeorder.OrderItem;

public class OrderFullInfo implements Serializable{
	private OrderInfo1 order;
	private List<OrderItemInfo> itemList;
	

	public OrderFullInfo(OrderInfo1 order, List<OrderItemInfo> itemList) {
		super();
		this.order = order;
		this.itemList = itemList;
	}

	public OrderFullInfo() {
		super();
	}

	public OrderInfo1 getOrder() {
		return order;
	}

	public void setOrder(OrderInfo1 order) {
		this.order = order;
	}

	public List<OrderItemInfo> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItemInfo> itemList) {
		this.itemList = itemList;
	}
}
