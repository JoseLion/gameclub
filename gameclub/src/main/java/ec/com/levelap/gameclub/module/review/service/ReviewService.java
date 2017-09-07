package ec.com.levelap.gameclub.module.review.service;

import java.util.Date;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.review.entity.Review;
import ec.com.levelap.gameclub.module.review.repository.ReviewRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;

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
	
	@Transactional
	public Loan sendReview(Review review) throws ServletException {
		Loan loan = loanRepo.findOne(review.getLoan().getId());
		PublicUser currentUser = publicUserService.getCurrentUser();
		
		if (currentUser.getId().longValue() == loan.getGamer().getId().longValue()) {
			review.setLenderReviwedOn(new Date());
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
		
		return loan;
	}

	public ReviewRepo getReviewRepo() {
		return reviewRepo;
	}
}
