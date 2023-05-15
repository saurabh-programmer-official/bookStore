package com.fabellus.bookstoreweb;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
/*
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket; 
*/
@SpringBootApplication
//@EnableWebMvc
public class BookStoreWebConfig implements WebMvcConfigurer{

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("WEB-INF/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
		
	}
/*	private ApiInfo getApiDetails() { 
		return new ApiInfo("JLC BookStore- API", 
		"BookSearchMS- API - part of BookStore",
		"1.0","https://www.jlcindia.com",
		
 
//		new Contact("Srinivas Dande", "https://www.jlcindia.com", "sri@jlcindia.com", null), 
		"Larsen and Turbo","API under Free to use License","www.fabellus.com"); 
		} 

	@Bean 
	public Docket apiDocket() {
		System.out.println("docket called");
		return 
				new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.fabellus.microservices.bookprice"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiDetails()); 
		} 
	/*
	 * 	//com.fabellus.microservices.bookprice //org.springframework.boot
	 */
		@Override 
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("resource handler called");
//		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/"); 
		registry.addResourceHandler("/webjars/** ").addResourceLocations("classpath:/META-INF/resources/webjars/"); 
	//	WebMvcConfigurer.super.addResourceHandlers(registry);

		} 
		@Bean("myOrderExchange")
		Exchange createOrdreExchange() {
			return ExchangeBuilder.topicExchange("myOrder.exchange").build();
		}
		@Bean("myOrderQueue")
		Queue createOrderQueue() {
			return QueueBuilder.durable("myOrder.queue").build();
		}
		
		@Bean
		Binding createOrderBinding(Queue myOrderQueue, TopicExchange myOrderExchange) {
			return BindingBuilder
					.bind(myOrderQueue)
					.to(myOrderExchange)
					.with("myOrder.key");
		}
		

		@Bean(name="myInventoryExchange")
		Exchange createInventoryExchange() {
			return ExchangeBuilder.topicExchange("myinventory.exchange").build();
		}

		
		@Bean(name="myInventoryQueue")
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


}
