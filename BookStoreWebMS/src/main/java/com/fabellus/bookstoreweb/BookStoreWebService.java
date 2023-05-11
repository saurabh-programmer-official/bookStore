package com.fabellus.bookstoreweb;

import java.util.List;
import java.util.Map;

public interface BookStoreWebService {

	public List<String>getAuthorList();
	public List<String>getCategoryList();
	
	public List<Book> getMyBook(String author, String category);
	public List<Book> getAllBooks();
	public BookInfo getBookByBookId(int bookId);
	public BookInfo getBookInfoByBookId(int bookId);
	
	public void placeOrder(Map<Integer,BookInfo> myCartMap);
	public List<Order>getMyOrders(String userId);
	public void addUserRating(UserRating userRating);
	public List<UserRating> getMyRatings(String userId);
}
