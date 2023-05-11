package com.fabellus.userrating;

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
public class UserRatingConfig implements WebMvcConfigurer{

	private ApiInfo getApiDetails() { 
		return new ApiInfo(
		"JLL BookStore- API",
		"BookSearchMS- API - part of BookStore",
		"1.0",
		"www.fabellus.com",
		new Contact("Saurabh Bhandari", "https://www.fabellus.com", "saurabh.bhandari@gmail.com"),
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

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		
		System.out.println("resource handler called");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/"); 
		registry.addResourceHandler("/webjars/** ").addResourceLocations("classpath:/META-INF/resources/webjars/"); 
	}
		
}
