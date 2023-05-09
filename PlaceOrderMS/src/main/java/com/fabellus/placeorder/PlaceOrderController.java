package com.fabellus.placeorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
//@ApiModel("PlaceOrder Controller class with 2 methods")
public class PlaceOrderController {

	@Autowired
	PlaceOrderService placeOrderService;
//	@ApiOperation(value = "getOrders",response = Order.class, notes = "List of orders")
	@GetMapping("/myorders/{userId}")
	public List<Order> getOrdersByUserId(@PathVariable String userId){
		System.out.println("Controller called");
		return placeOrderService.getOrdersByUserId(userId);
		
	}
	/*
	@PostMapping("/placeorder")
	@ApiOperation(value = "placeOrders",response = Order.class, notes = "List of orders")
	public Order placeholder(@RequestBody OrderInfo orderInfo) {
		System.out.println("PlaceOrdr MS: REst COntroller");
		Order order=placeOrderService.placeOrder(orderInfo);
		return order;
	}
	*/
}
