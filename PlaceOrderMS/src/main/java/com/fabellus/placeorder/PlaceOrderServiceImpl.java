package com.fabellus.placeorder;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabellus.rabbitmq.BookInventoryInfo;
import com.fabellus.rabbitmq.OrderFullInfo;
import com.fabellus.rabbitmq.OrderInfo1;
import com.fabellus.rabbitmq.OrderItemInfo;

@Service
@Transactional
public class PlaceOrderServiceImpl implements PlaceOrderService{

	@Autowired
	OrderDAO orderDAO;
	@Autowired
	OrderItemDAO orderItemDAO;
	@Autowired
	BookInventoryDAO bookInventoryDAO;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public List<Order> getOrdersByUserId(String userId) {
	// TODO Auto-generated method stub
	List<Order> orderList = orderDAO.findOrdersByUserId(userId);
		return orderList;

	}
	
	@RabbitListener(queues="myOrder.queue")
	public void placeOrder(OrderFullInfo orderInfo) {
		// TODO Auto-generated method stub
		//Save the order
		//Order info is coming from front end and order is transient object for now
		System.out.println("PLaceholder rabbitMQ methd called");
		OrderInfo1 myorder = orderInfo.getOrder();
		Order order = new Order(myorder.getOrderDate(),myorder.getUserId(),myorder.getTotalQty(),myorder.getTotalCost(),myorder.getStatus());
		Order ordersaved = orderDAO.save(order);//Primary key gets generated
		//Insert Order Item
		
		List<OrderItemInfo> itemListInfo =orderInfo.getItemList();
		for(OrderItemInfo orderItemInfo: itemListInfo) {
		OrderItem orderItempojo = new OrderItem(orderItemInfo.getOrderItemId(),ordersaved.getOrderId(),orderItemInfo.getBookId(),orderItemInfo.getQty(),orderItemInfo.getCost());
		orderItemDAO.save(orderItempojo);
		}

		/*
		int orderId  = myorder.getOrderId();
		
		//Insert Order Item
		List<OrderItemInfo> itemListInfo  = orderInfo.getItemList();
		for(OrderItemInfo orderItemInfo: itemListInfo) {
			OrderItem myorderItem = new OrderItem(orderId, orderItemInfo.getOrderItemId(),orderItemInfo.getQty(), orderItemInfo.getCost());
			orderItemDAO.save(myorderItem);
		}

/*	
		/*
		 * attach order id to each order item.
		 * Again the itemlist coming form front end does not have any primary key
		 */
		for(OrderItemInfo orderItemInfo:itemListInfo) {

			// Update the local inventory
			/*
			 * From DeSearialised OrderItemInfo object, get bookId 
			 * and for respective bookId get inventory count of that book
			 * */
			int bookId = orderItemInfo.getBookId();
			BookInventory bookInventory = bookInventoryDAO.findById(bookId).get();
			bookInventory.setBookAvailable(bookInventory.getBookAvailable()-orderItemInfo.getQty());
			bookInventory = bookInventoryDAO.save(bookInventory);

			//Update Remote Inventory
			BookInventoryInfo bookInventoryInfo = new BookInventoryInfo();
			bookInventoryInfo.setBookId(bookInventory.getBookId());
			bookInventoryInfo.setBookAvailable(bookInventory.getBookAvailable());
			System.out.println("passing from Placeorder exchange: "+ bookInventoryInfo);
		//	rabbitTemplate.convert
			rabbitTemplate.convertAndSend("myinventory.exchange","myinventory.key",bookInventoryInfo);
		}

	}
}
