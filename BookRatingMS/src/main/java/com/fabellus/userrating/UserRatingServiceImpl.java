package com.fabellus.userrating;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabellus.rabbitmq.BookRatingInfo;
import com.fabellus.rabbitmq.UserRatingInfo;

@Service
@Transactional
public class UserRatingServiceImpl implements UserRatingService{

	@Autowired
	UserRatingDAO userRatingDAO;
	@Autowired
	BookRatingDAO bookRatingDAO;
	@Autowired
	RabbitTemplate rabbitTemplate;

	@RabbitListener(queues="userRating.queue")
	public void addUserRating(UserRatingInfo userRatingInfo) {
		// TODO Auto-generated method stub
		
		UserRating userRating = userRatingDAO.save(
				new UserRating(
						userRatingInfo.getBookId(),
						userRatingInfo.getUserId(),
						userRatingInfo.getRating(),
						userRatingInfo.getReview()
						)
				);//User rating saved.
		 
		int bookId = userRating.getBookId();
		System.out.println("bookId: "+bookId);
		double rating= userRating.getRating();
		List<UserRating> userRatingListByBookId = userRatingDAO.getUserRatingsByBookId(bookId);
		double sum = 0;
		for(UserRating userRatingByBookId: userRatingListByBookId) {
			sum = sum + userRatingByBookId.getRating();
		}
		double average = sum/userRatingListByBookId.size();
		System.out.println("avg"+ average);
		
		//Update Local database of Rating
		
		Optional<BookRating>opt = bookRatingDAO.findById(bookId);
		BookRating bookRating	=null;
		if(opt.isPresent()) {
		bookRating	= opt.get();
		bookRating.setRating((average));//setter method
		bookRatingDAO.save(bookRating); //persistence operation
		}else
		{
		 bookRating	= new BookRating(); //new BookRating object made
		 bookRating.setBookId(bookId);
		 bookRating.setRating(rating);
		 bookRating.setNumber_of_searches(50);
		 bookRatingDAO.save(bookRating);
		}
		//Updating Remote DataBase running in other microservices
		BookRatingInfo bookRatingInfo = new BookRatingInfo();
		bookRatingInfo.setBookId(bookRating.getBookId());
		bookRatingInfo.setRating(bookRating.getRating());
		bookRatingInfo.setNumber_of_searches(bookRating.getNumber_of_searches());
		
		rabbitTemplate.convertAndSend("ratings.exchange", "ratings.key", bookRatingInfo);
		
		//Remote database updating using rest template
//		RestTemplate ratingTemplate = new RestTemplate();
//		String url ="http://localhost:7000/updaterating";
//		ratingTemplate.put(url, bookRating);
	}
	
	public List<UserRating> getUserRatingsByBookId(int bookId){
		return userRatingDAO.getUserRatingsByBookId(bookId);		
	}
	public List<UserRating> getUserRatingsByUserId(String userId){
		return userRatingDAO.getUserRatingsByUserId(userId);
		
	}
}
