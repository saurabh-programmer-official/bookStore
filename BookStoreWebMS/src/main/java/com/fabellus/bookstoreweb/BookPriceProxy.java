package com.fabellus.bookstoreweb;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient("MyApiGateWayServer")
public interface BookPriceProxy {

	/*
	@GetMapping("/my-book-price/mybooks/{bookid}")
	public BookPriceInfo getBookPriceObject(@PathVariable int bookid);
	
	
	@GetMapping("/my-book-price/offeredPrice/{bookid}")
	public double getBookOfferPrice(@PathVariable int bookid) ;
	*/
}
