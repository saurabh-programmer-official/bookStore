package com.fabellus.booksearch;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MyApiGateWayServer")
public interface BookPriceProxy {

	@GetMapping("/my-book-price/bookPrice/{bookId}")
	public BookPriceInfo getBookPriceInfo(@PathVariable("bookId") int bookId);
	
	@GetMapping("/my-book-price/offeredPrice/{bookId}")
	public double getOfferedPrice(@PathVariable("bookId") int bookId);
}
