package com.fabellus.microservices.bookprice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
public class BookPriceController {

	@Autowired
	BookPriceService bookPriceService;


	@GetMapping("/bookPrice/{bookId}")
	public BookPriceInfo geBookPriceInfo(@PathVariable int bookId)
	{
		System.out.println("BookPriceCOntroller: ");

		return bookPriceService.getBookPriceInfo(bookId);
	}
	
	@GetMapping("/offeredPrice/{bookId}")
	public double getOfferedPrice(@PathVariable int bookId) {
		return 0;
		}
}
