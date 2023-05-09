package com.fabellus.bookstoreweb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fabellus.rabbitmq.OrderFullInfo;
import com.fabellus.rabbitmq.OrderInfo1;
import com.fabellus.rabbitmq.OrderItemInfo;
import com.fabellus.rabbitmq.UserRatingInfo;


@Service
@Transactional
public class BookStoreWebServiceImpl implements BookStoreWebService{
	static Logger log = LoggerFactory.getLogger(BookStoreWebServiceImpl.class);

	Map<Integer,Book> booksMap = new LinkedHashMap<>();
	
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired(required = false)
	BookPriceProxy bookPriceProxy;
	
	@Autowired
	BookSearchProxy bookSearchProxy;
	
	@Autowired
	PlaceOrderProxy placeOrderProxy;
	
	@Autowired
	UserRatingProxy userRatingProxy;
	
@Override
public void addUserRating(UserRating userRating) {
	//Add UserRating 
	//TODO Auto-generated method stub
	/*
	 * Step 1 convert the userRatingObject into Serializable userRating Info object
	 * send using rabbitTemplate convert and Send method.
	 * */
	UserRatingInfo userRatingInfo = new UserRatingInfo(userRating.getRatingId(),userRating.getBookId(),userRating.getUserId(),userRating.getRating(),userRating.getReview());
	System.out.println("add User Rating"+ userRatingInfo);

	rabbitTemplate.convertAndSend("userRating.exchange","userRating.key", userRatingInfo);
	/*
	 * RestTemplate restTemplate = new RestTemplate();
	String url ="http://localhost:5000/addUserRating";
	restTemplate.postForObject(url, userRating, void.class);
	*/
	
}
@Override
public List<String> getAuthorList() {
	// TODO Auto-generated method stub
	List<String> authorList = new ArrayList<>();
	authorList.add("All Authors");
	authorList.add("Srinivas Dande");
	authorList.add("Saurabh B");
	authorList.add("Radhika B");
	return authorList;
}
@Override
public List<Book> getAllBooks() {
	// TODO Auto-generated method stub
/*	RestTemplate restTemplate = new RestTemplate();
	String url ="http://localhost:7000/mybooks";
*/
//	List<Book> bookList = restTemplate.getForObject(url, List.class);
	List<Book> bookList = bookSearchProxy.getAllBooks();

	return bookList;
}
@Override
public List<String> getCategoryList() {
	// TODO Auto-generated method stub
	List<String> catList=new ArrayList<>(); 
	catList.add("All Categories"); 
	catList.add("Web"); 
	catList.add("Spring"); 
	catList.add("Server");
	catList.add("Database");
	catList.add("Finance");
	catList.add("Marketing");
	return catList; 
}
@Override
public Book getBookByBookId(int bookId) {
	// TODO Auto-generated method stub
	RestTemplate restTemplate = new RestTemplate();
	String url ="http://localhost:7000/mybookOnly/"+bookId;
	Book book = restTemplate.getForObject(url, Book.class);
	return book; 
}

@Override
public BookInfo getBookInfoByBookId(int bookId) {
	// TODO Auto-generated method stub
	System.out.println("Book Info in service IMPL");
/*	RestTemplate restTemplate = new RestTemplate();
	String url ="http://localhost:7000/mybookInfo/"+bookId;
	BookInfo book=  restTemplate.getForObject(url,BookInfo.class);
*/
	// Invoking BookSearch Rest API using Feign
	BookInfo book=bookSearchProxy.getBookInfo(bookId);
	return book;
}
	 
@Override
public List<Book> getMyBook(String author, String category) {
	// TODO Auto-generated method stub
	if(author==null || author.length()==0){
		author="All Authors";
	}
	if(category==null|| category.length()==0) {
		category="All Categories";
	}
/*	Using RestTemplate to invoke BookSearch API Rest end points
 * 	
 * 	RestTemplate restTemplate = new RestTemplate();
	
	String url ="http://localhost:7000/mybooks/author/"+author+"/category/"+category;
	
	List<Book> books = restTemplate.getForObject(url, List.class);  
*/	
	//Invoking BookSearch Rest API using Feign
	List<Book> bookList = bookSearchProxy.getBooksByAuthorAndCategory(author, category);
	
	return  bookList;
}

@Override
public List<Order> getMyOrders(String userId) {
	// TODO Auto-generated method stub
/*	RestTemplate restTemplate = new RestTemplate();
	String url ="http://localhost:6002/myorders/"+userId;
	
	
	return restTemplate.getForObject(url, List.class);
	*/
	// Invoking PlaceOrderMS Rest API using Feign
	return placeOrderProxy.getOrdersByUserId(userId);
}

@Override
public List<UserRating> getMyRatings(String userId) {
	// TODO Auto-generated method stub
/*	
 * 	Invoking UserRatingMS using Rest Template
 * 	RestTemplate restTemplate = new RestTemplate();
	String url ="http://localhost:5000/getUserRatings/"+userId;
	return restTemplate.getForObject(url, List.class);
*/
	//Invoking UserRating MicroService rest API using Feign
	return userRatingProxy.getUserRatingByUserId(userId);

}

@Override
public void placeOrder(Map<Integer,Book> myCartMap){
	// TODO Auto-generated method stub
	List<OrderItemInfo> orderItems = new ArrayList<>();
	int totalQuantity = 0;
	double totalCost = 0;
	
	// Taking Collection from Map using values() method
	Collection<Book> books = myCartMap.values();
	for(Book book:books){
		System.out.println("Placeorder in BookStore IMPL"+book);
		Integer bookId = book.getBookId();
/*		
 * 		Invoking BookPrice REST API using REST Template
 * 		RestTemplate bookPriceRest = new RestTemplate();
		
		//Rest call to fetch price
		String priceEndPoint = "http://localhost:3003/offeredPrice/"+bookId;
		double offerPrice = bookPriceRest.getForObject(priceEndPoint, double.class);
*/
		//Invoking BookPrice REST API using Feign
		double offerPrice = bookSearchProxy.getBookOfferPrice(bookId);
				//bookPriceProxy.getBookOfferPrice(bookId);
		totalCost=offerPrice+totalCost;
		System.out.println("Offer price Placeorder in BookStore IMPL"+offerPrice);
		OrderItemInfo orderItem = new OrderItemInfo(0,bookId,1,offerPrice);
		orderItems.add(orderItem);
		totalQuantity = totalQuantity+1;
	}
	System.out.println("Offer price Placeorder in BookStore IMPL"+totalCost);
	
	Date today = Calendar.getInstance().getTime(); 
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm"); 
	String orderDate = formatter.format(today); 
	System.out.println(orderDate); 
	OrderInfo1 order = new OrderInfo1(orderDate,"U-111",totalQuantity,totalCost,"New");
	/*
	 * This code was valid when using Rest
	 OrderInfo orderInfo = new OrderInfo(); 
	orderInfo.setOrder(order);
	orderInfo.setItemList(orderItems);
	*/
	
	OrderFullInfo orderFullInfo = new OrderFullInfo(); 
	orderFullInfo.setOrder(order); 
	orderFullInfo.setItemList(orderItems); 
	System.out.println("Order full info: "+ orderFullInfo);
	
	//Using rabbitTemplate to send the message to the respective Microservice using exchange and key
	
	rabbitTemplate.convertAndSend("myOrder.exchange",
			"myOrder.key",
			orderFullInfo);
	
	System.out.println("Order Place Successfully");

	/*
	RestTemplate placeOrderRest = new RestTemplate();
	String priceEndPoint = "http://localhost:6002/placeorder";
	placeOrderRest.postForObject(priceEndPoint,orderInfo, Order.class);
	System.out.println("Order Place Successfully");
	List<OrderItem>orderItemList = orderInfo.getItemList();
	for(OrderItem orderItem:orderItemList) {
		int bookId = orderItem.getBookId();
		RestTemplate bookPriceRest = new RestTemplate();
		String priceEndPoint = "http://localhost:9001/offeredPrice"+bookId;
		double offerPrice = bookPriceRest.getForObject(priceEndPoint, double.class);
	}
	RestTemplate restTemplate = new RestTemplate();
	String url ="http://localhost:6002/
	*/
	
}
}
