package com.fabellus.booksearch;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/*
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket; 
*/


@SpringBootApplication
public class BookSearchConfig implements WebMvcConfigurer{

	private ApiInfo getApiDetails() { 
		return new ApiInfo(
		"JLL BookStore- API", 
		"BookSearchMS- API - part of BookStore",
		"1.0",
		"www.fabellus.com",
		new Contact(
				"Srinivas Dande",
				"https://www.jlcindia.com",
				"sri@jlcindia.com"
				),
		"Larsen and Turbo",
		"API under Free to use License"
		); 
		} 

	@Bean 
	 Docket apiDocket() {
		System.out.println("docket called");
		return 
				new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.fabellus.userrating"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiDetails()); 
		} 
		//com.fabellus.microservices.bookprice //org.springframework.boot

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		
		System.out.println("resource handler called");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/"); 
		registry.addResourceHandler("/webjars/** ").addResourceLocations("classpath:/META-INF/resources/webjars/"); 
	}
		
	@Bean(name="myUserRatingExchange")
	Exchange createUserRatingExchange() {
		return ExchangeBuilder.topicExchange("userRating.exchange").build();
	}
	
	@Bean(name="myUserRatingQueue")
	Queue createUserRatingQueue() {
		return QueueBuilder.durable("userRating.queue").build();
	}
	@Bean
	Binding bindUserRating(Queue myUserRatingQueue, TopicExchange myUserRRatingExchange) {
		return BindingBuilder.bind(myUserRatingQueue).to(myUserRRatingExchange).with("userRating.key");
	}
	
	@Bean(name="myRatingsExchange")
	Exchange createExchange() {
		return ExchangeBuilder.topicExchange("ratings.exchange").build();
		
	}
	@Bean(name="myRatingsQueue")
	Queue createQueue() {
		return QueueBuilder.durable("ratings.queue").build();
	}
	
	@Bean
	Binding bindingQueue(Queue myRatingsQueue,TopicExchange myRatingsExchange) {
		return BindingBuilder.bind(myRatingsQueue).to(myRatingsExchange).with("ratings.key");
	}
	
	@Bean("myInventoryExchange")
	Exchange createInventoryExchange() {
		return ExchangeBuilder.topicExchange("myinventory.exchange").build();
	}
	
	@Bean("myInventoryQueue")
	Queue createInventoryQueue() {
		return QueueBuilder.durable("myinventory.queue").build();
	}
	
	@Bean
	Binding inventoryBinding(Queue myInventoryQueue, TopicExchange myInventoryExchange) {
		return BindingBuilder
				.bind(myInventoryQueue)
				.to(myInventoryExchange)
				.with("myinventory.key");
	} 

}
