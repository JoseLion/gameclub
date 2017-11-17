package ec.com.levelap.gameclub.module.review.controller;

import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.review.service.ReviewService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

@RestController
@RequestMapping(value="api/review", produces=MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<Loan> save(@RequestBody Loan loan) throws ServletException {
		return new ResponseEntity<Loan>(reviewService.save(loan), HttpStatus.OK);
	}
	
	@RequestMapping(value="getReviewsOfCurrentUser/{page}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getReviewsOfCurrentUser(@PathVariable Integer page) throws ServletException {
		PublicUser currentUser = publicUserService.getCurrentUser();
		Map<String, Object> response = reviewService.getReviewsOfUser(currentUser.getId(), page);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="getReviewsOfUser/{id}/{page}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getReviewsOfUser(@PathVariable Long id, @PathVariable Integer page) throws ServletException {
		Map<String, Object> response = reviewService.getReviewsOfUser(id, page);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
