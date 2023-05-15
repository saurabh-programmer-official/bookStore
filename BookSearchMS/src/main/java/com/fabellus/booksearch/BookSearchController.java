
package com.fabellus.booksearch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
	BookSearchService bookSearchService;
	

	@Autowired(required = false)
	RestTemplate restTemplate;
	
	@GetMapping("/mybooks/author/{author}/category/{category}") 
	@ApiOperation(value = " getBooks", response = List.class, notes = "Returns List of Books for given Author and Category") 
	public List<Book> getBooks(@PathVariable String author, @PathVariable String category) { 
	log.info("---BookController---getBooks()-----"); 
	System.out.println(author + "\t" + category); 
	return bookSearchService.getBooksByAuthorAndCategory(author, category); 
	} 
	
	@GetMapping("/mybook/{bookId}") 
	@ApiOperation(value = " getBookById", response = BookInfo.class, notes = "Returns BookInfo for given BID") 
	public BookInfo getBookInfoById(@PathVariable Integer bookId) {
		
	log.info("---BookController---getBookById()-----");
	BookInfo book = bookSearchService.getBookInfoById(bookId);
	System.out.println("BookSearchCOntroller bookInfo "+book );
	return book;
	
	} 
	
	@PutMapping("/updateBookRating") 
	@ApiOperation(value = " updateBookRating", response = void.class, notes = 
	"updateBookRating") 
	public void updateBookRating(@RequestBody BookRating bookRating) { 
	System.out.println("-------BookController-----updateBookRating()-----"); 
//	bookSearchService.updateBookRating(bookRating); 
	} 
	
	@PutMapping("/updateBookInventory") 
	@ApiOperation(value = " updateBookInventory", response = void.class, notes = 
	"updateBookInventory") 
	public void updateBookInventory(@RequestBody BookInventory bookInventory) { 
	System.out.println("-------BookController-----updateBookInventory()-----"); 
//	bookSearchService.updateBookInventory(bookInventory); 
	}
	
	@GetMapping("/mybooks")
	public List<Book> getAllBooks(){
		System.out.println("BookSearch COntroller");
		return bookSearchService.getAllBooks();
	}
	
	}