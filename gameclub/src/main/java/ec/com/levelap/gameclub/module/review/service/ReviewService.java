package ec.com.levelap.gameclub.module.review.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.review.entity.Review;
import ec.com.levelap.gameclub.module.review.repository.ReviewRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.repository.PublicUserGameRepo;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
	private LoanRepo loanRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private PublicUserGameRepo publicUserGameRepo;
	
	@Transactional
	public Loan sendReview(Review review) throws ServletException {
		Loan loan = loanRepo.findOne(review.getLoan().getId());
		PublicUser currentUser = publicUserService.getCurrentUser();
		
		if (currentUser.getId().longValue() == loan.getGamer().getId().longValue()) {
			review.setLenderReviwedOn(new Date());
			
			review.getLoan().getPublicUserGame().setIsBorrowed(false);
			review.getLoan().setPublicUserGame(publicUserGameRepo.save(review.getLoan().getPublicUserGame()));
		} else {
			review.setGamerReviwedOn(new Date());
		}
		
		review = reviewRepo.saveAndFlush(review);
		
		if (review.getFinished()) {
			loan.getGamerMessage().setRead(false);
			loan.getLenderMessage().setRead(false);
			messageRepo.save(loan.getGamerMessage());
			messageRepo.save(loan.getLenderMessage());
		}
		
		loan.setReview(review);
		return loan;
	}
	
	@Transactional
	public Loan acceptReview(Long id) throws ServletException {
		Review review = reviewRepo.findOne(id);
		PublicUser currentUser = publicUserService.getCurrentUser();
		
		if (currentUser.getId().longValue() == review.getLoan().getGamer().getId().longValue()) {
			review.setGamerAccepted(true);
		} else {
			review.setLenderAccepted(true);
		}
		
		review = reviewRepo.save(review);
		Loan loan = loanRepo.findOne(review.getLoan().getId());
		loan.setReview(review);
		
		if (currentUser.getId().longValue() == review.getLoan().getGamer().getId().longValue()) {
			Double gamerAverage = reviewRepo.getGamerAverageScore(currentUser.getId());
			Double lenderAverage = reviewRepo.getLenderAverageScore(currentUser.getId());
			currentUser.setRating((gamerAverage.doubleValue() + lenderAverage.doubleValue()) / 2.0);
			publicUserService.getPublicUserRepo().save(currentUser);
		} else {
			Double gamerAverage = reviewRepo.getGamerAverageScore(review.getLoan().getPublicUserGame().getPublicUser().getId());
			Double lenderAverage = reviewRepo.getLenderAverageScore(review.getLoan().getPublicUserGame().getPublicUser().getId());
			review.getLoan().getPublicUserGame().getPublicUser().setRating((gamerAverage.doubleValue() + lenderAverage.doubleValue()) / 2.0);
			publicUserService.getPublicUserRepo().save(review.getLoan().getPublicUserGame().getPublicUser());
		}
		
		return loan;
	}
	
	@Transactional
	public Map<String, Object> getReviewsOfUser(Long userId, Integer page) throws ServletException {
		Page<Review> reviews = reviewRepo.findReviewsOfUser(userId, new PageRequest(page, Const.TABLE_SIZE));
		Long gamerReviews = reviewRepo.countByGamerAcceptedIsTrueAndLoanGamerId(userId);
		Long lenderReviews = reviewRepo.countByLenderAcceptedIsTrueAndLoanPublicUserGamePublicUserId(userId);
		Double gamerAverage = reviewRepo.getGamerAverageScore(userId);
		Double lenderAverage = reviewRepo.getLenderAverageScore(userId);
		
		Map<String, Object> response = new HashMap<>();
		response.put("reviews", reviews);
		response.put("gamerReviews", gamerReviews);
		response.put("lenderReviews", lenderReviews);
		response.put("gamerAverage", gamerAverage);
		response.put("lenderAverage", lenderAverage);
		
		return response;
	}

	public ReviewRepo getReviewRepo() {
		return reviewRepo;
	}
}
