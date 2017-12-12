package ec.com.levelap.gameclub.module.review.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.service.LoanService;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.review.entity.Review;
import ec.com.levelap.gameclub.module.review.repository.ReviewRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.repository.PublicUserRepo;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private LevelapCryptography cryptoService;
	
	@Autowired
	private PublicUserRepo publicUserRepo;
	
	@Transactional
	public Loan save(Loan loan) throws ServletException, IOException, GeneralSecurityException, MessagingException {
		PublicUser currentUser = publicUserService.getCurrentUser();
		byte[] keyEncript = publicUserRepo.findKey(loan.getGamer().getId());
		
		if (currentUser.getId().longValue() == loan.getGamer().getId().longValue()) {
			loan.getReview().setLenderReviewDate(new Date());
			loan.getPublicUserGame().setIsBorrowed(false);
			publicUserService.getPublicUserGameRepo().save(loan.getPublicUserGame());
			
			loan.getLenderMessage().setRead(false);
			messageService.getMessageRepo().save(loan.getLenderMessage());
			
			Double gamingAverage = reviewRepo.getGamingAverageOfUser(currentUser.getId());
			Double lendingAverage = reviewRepo.getLendingAverageOfUser(currentUser.getId());
			
			PublicUser lender = publicUserService.getPublicUserRepo().findOne(loan.getPublicUserGame().getPublicUser().getId());
			lender.setRating((gamingAverage + lendingAverage) / 2.0);
			publicUserService.getPublicUserRepo().save(lender);
		} else {
			loan.getReview().setGamerReviewDate(new Date());
			
			loan.getGamerMessage().setRead(false);
			messageService.getMessageRepo().save(loan.getGamerMessage());
			
			Double gamerAverage = reviewRepo.getGamingAverageOfUser(loan.getPublicUserGame().getPublicUser().getId());
			Double lenderAverage = reviewRepo.getLendingAverageOfUser(loan.getPublicUserGame().getPublicUser().getId());
			
			PublicUser gamer = publicUserService.getPublicUserRepo().findOne(loan.getGamer().getId());
			gamer.setRating((gamerAverage + lenderAverage) / 2.0);
			publicUserService.getPublicUserRepo().save(gamer);
		}
		
		File keyGamer = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(keyGamer, keyEncript);

		loan.setCostEnc(cryptoService.encrypt(Double.toString(loan.getCost()), keyGamer));
		loan.setBalancePartEnc(cryptoService.encrypt(Double.toString(loan.getBalancePart()), keyGamer));
		loan.setCardPartEnc(cryptoService.encrypt(Double.toString(loan.getCardPart()), keyGamer));
		
		loan.setShippningCostEnc(cryptoService.encrypt(Double.toString(loan.getShippningCost()), keyGamer));
		loan.setFeeGameClubEnc(cryptoService.encrypt(Double.toString(loan.getFeeGameClub()), keyGamer));
		loan.setTaxesEnc(cryptoService.encrypt(Double.toString(loan.getTaxes()), keyGamer));
		
		loan = loanService.getLoanRepo().save(loan);
		return loan;
	}
	
	@Transactional
	public Map<String, Object> getReviewsOfUser(Long userId, Integer page) throws ServletException {
		Page<Review> reviews = reviewRepo.findReviewsOfUser(userId, new PageRequest(page, Const.TABLE_SIZE));
		Long gamerReviews = reviewRepo.countByLoanGamerId(userId);
		Long lenderReviews = reviewRepo.countByLoanPublicUserGamePublicUserId(userId);
		Double gamerAverage = reviewRepo.getGamingAverageOfUser(userId);
		Double lenderAverage = reviewRepo.getLendingAverageOfUser(userId);
		
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
