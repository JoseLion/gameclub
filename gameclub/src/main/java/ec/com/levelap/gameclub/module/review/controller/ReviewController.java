package ec.com.levelap.gameclub.module.review.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.review.entity.Review;
import ec.com.levelap.gameclub.module.review.service.ReviewService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/review", produces=MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@RequestMapping(value="sendReview", method=RequestMethod.POST)
	public ResponseEntity<Loan> sendReview(@RequestBody Review review) throws ServletException {
		Loan loan = reviewService.sendReview(review);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="acceptReview/{id}", method=RequestMethod.GET)
	public ResponseEntity<Loan> acceptReview(@PathVariable Long id) throws ServletException {
		Loan loan = reviewService.acceptReview(id);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="getReviewsOfCurrentUser/{page}", method=RequestMethod.GET)
	public ResponseEntity<Page<Review>> getReviewsOfCurrentUser(@PathVariable Integer page) throws ServletException {
		PublicUser currentUser = publicUserService.getCurrentUser();
		Page<Review> reviews = reviewService.getReviewRepo().findReviewsOfUser(currentUser.getId(), new PageRequest(page, Const.TABLE_SIZE));
		System.out.println("GAMER SCORE: " + reviews.getContent().get(0).getGamerScore());
		return new ResponseEntity<Page<Review>>(reviews, HttpStatus.OK);
	}
	
	@RequestMapping(value="getReviewsOfUser/{id}/{page}", method=RequestMethod.GET)
	public ResponseEntity<Page<Review>> getReviewsOfUser(@PathVariable Long id, @PathVariable Integer page) throws ServletException {
		Page<Review> reviews = reviewService.getReviewRepo().findReviewsOfUser(id, new PageRequest(page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<Review>>(reviews, HttpStatus.OK);
	}
}
