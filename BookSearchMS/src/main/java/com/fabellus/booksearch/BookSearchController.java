
package com.fabellus.booksearch;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
/*
 import io.swagger.annotations.ApiModel;
 import io.swagger.annotations.ApiOperation;
*/
@CrossOrigin
@RestController
public class BookSearchController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	BookPriceProxy bookPriceProxy;
	
	@Value("${server.port}")
	String bookSearchServerPort;
	
	@Autowired
	BookSearchService bookSearchService;
	
/*	
	@Autowired
	LoadBalancerClient loadBalancerClient;
	
	@Autowired
	DiscoveryClient discoveryClient;

	@Autowired
	RestTemplate restTemplate;
*/	
	
	@GetMapping("/mybookInfo/{bookId}")
	public BookInfo getBookInfo(@PathVariable int bookId){

		log.info("--BookSearchController--getBookInfo--");
		BookInfo bookInfo = new BookInfo();
		bookInfo.setBookId(bookId);
		bookInfo.setBookName("Master MicorServices");
		bookInfo.setAuthor("Srininvas Dande");
		bookInfo.setCategory("Java");
		bookInfo.setPublication("JLC");
		bookInfo.setBookSearchServerPort(bookSearchServerPort);
		
		//Start Here
		//Option 6: Using Feign and Eureka
		
		BookPriceInfo bookPriceInfo=bookPriceProxy.getBookPriceInfo(bookId);
		/*		
		//Option5 LoadBalancerClient
		ServiceInstance serviceInstance=loadBalancerClient.choose("MyBookPriceMS");
		String baseUrl = serviceInstance.getUri().toString();
		String apiUrl = "/bookPrice/"+bookId;
		String endPoint = baseUrl+apiUrl;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<BookPriceInfo> responseEntity = restTemplate.getForEntity(endPoint, BookPriceInfo.class);
		BookPriceInfo bookPriceInfo = responseEntity.getBody();		
		//LoadBalancer REstTemplate
		//opt4 Make BaseURL with MicroService name directly, dont need to give the instance address directly
 		String baseUrl="http://MyBookPriceMS";
			String apiUrl = "/bookPrice/"+bookId;
			String endPoint = baseUrl+apiUrl;
			ResponseEntity<BookPriceInfo> responseEntity = restTemplate.getForEntity(endPoint, BookPriceInfo.class);
			BookPriceInfo  bookPriceInfo = responseEntity.getBody();
*/		
		bookInfo.setPrice(bookPriceInfo.getPrice());
		bookInfo.setOffer(bookPriceInfo.getOffer());
		bookInfo.setBookPriceServerPort(bookPriceInfo.getBookPriceServerPort());

		log.info("bookInfo object: "+bookInfo.toString());
		return bookInfo;
		
//		return bookSearchService.getBookInfoById(bookId);
	}
	
	@GetMapping("/mybooks/{author}")
	public BookInfo getBooksByAuthor(@PathVariable String author){
		log.info("--BookSearchController--getBooksByAUthor--");
		try {
			if(1==1) {
				throw new ArithmeticException();
			}
			
		}catch(Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String myerrorTrace = sw.toString();
			log.error("My Exception: "+myerrorTrace);

		}
		return null;
		
	}
	@GetMapping("/mybooks")
	@ApiOperation(value="getAllBooks()",notes = "get all books")
	public List<Book> getAllBooks(){
		System.out.println("BookSearchController getAllBooks");
		return bookSearchService.getAllBooks();
	}
}
