package ec.com.levelap.gameclub.module.review.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.review.entity.Review;
import ec.com.levelap.gameclub.module.review.service.ReviewService;

@RestController
@RequestMapping(value="api/review", produces=MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	@RequestMapping(value="sendReview", method=RequestMethod.POST)
	public ResponseEntity<Loan> sendReview(@RequestBody Review review) throws ServletException {
		Loan loan = reviewService.sendReview(review);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
}
