package ec.com.levelap.gameclub.module.restore.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.service.FineService;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.service.LoanService;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.entity.RestoreLite;
import ec.com.levelap.gameclub.module.restore.repository.RestoreRepo;
import ec.com.levelap.gameclub.module.settings.entity.Setting;
import ec.com.levelap.gameclub.module.settings.service.SettingService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;

@Service
public class RestoreService {
	@Autowired
	private RestoreRepo restoreRepo;

	@Autowired
	private LoanService loanService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private PublicUserService publicUserService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private FineService fineService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private LevelapCryptography cryptoService;

	@Transactional
	public RestoreLite save(Restore restore, HttpSession session, HttpServletRequest request) throws ServletException, GeneralSecurityException, IOException, RestClientException, URISyntaxException {
		Restore previous = restoreRepo.findOne(restore.getId());
		restore.setLoan(previous.getLoan());

		if (!restore.getShippingStatus().equals(previous.getShippingStatus()) || (restore.getShippingNote() != null && !restore.getShippingNote().equalsIgnoreCase(previous.getShippingNote()))) {
			restore.setLenderStatusDate(new Date());
			restore.setGamerStatusDate(new Date());

			restore.getLenderMessage().setRead(false);
			restore.getGamerMessage().setRead(false);
			messageService.getMessageRepo().save(restore.getLenderMessage());
			messageService.getMessageRepo().save(restore.getGamerMessage());
		}

		PublicUser gamer = publicUserService.getPublicUserRepo().findOne(restore.getGamer().getId());
		File gamerKey = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(gamerKey, gamer.getPrivateKey());

		PublicUser lender = publicUserService.getPublicUserRepo().findOne(restore.getPublicUserGame().getPublicUser().getId());
		File lenderKey = File.createTempFile("keyLender", ".tmp");
		FileUtils.writeByteArrayToFile(lenderKey, lender.getPrivateKey());
		
		Double subtotal = (restore.getLoan().getPublicUserGame().getCost() * restore.getLoan().getWeeks()) + restore.getLoan().getShippningCost() + restore.getLoan().getFeeGameClub();

		if (restore.getShippingStatus().getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER)) {
			Setting fineSetting = settingService.getSettingsRepo().findByCode(Code.SETTING_GAMER_DIDNT_DELIVER);
			Double fineAmount;
			
			if (fineSetting.getType().equals(Const.SETTINGS_PERCENTAGE)) {
				fineAmount = subtotal * (Double.parseDouble(fineSetting.getValue()) / 100.0);
			} else {
				fineAmount = Double.parseDouble(fineSetting.getValue());
			}
			
			Fine fine = new Fine();
			fine.setOwner(gamer);
			fine.setAmountEnc(cryptoService.encrypt(Double.toString(fineAmount), gamerKey));
			fine.setDescription(restore.getShippingStatus().getName());
			fineService.getFineRepo().save(fine);
			
			Setting rewardSetting = settingService.getSettingsRepo().findByCode(Code.SETTING_LENDER_REWARD_ON_DELAY);
			Double rewardAmount;
			
			if (rewardSetting.getType().equals(Const.SETTINGS_PERCENTAGE)) {
				rewardAmount = restore.getLoan().getPublicUserGame().getCost() * (Double.parseDouble(rewardSetting.getValue()) / 100.0);
			} else {
				rewardAmount = Double.parseDouble(rewardSetting.getValue());
			}
			
			publicUserService.addToUserBalance(lender.getId(), rewardAmount);
			
			Transaction transaction = new Transaction();
			transaction.setOwner(lender);
			transaction.setTransaction("RECOMPENSA POR DEMORA");
			transaction.setGame(restore.getLoan().getPublicUserGame().getGame().getName());
			transaction.setConsole(restore.getLoan().getPublicUserGame().getConsole().getName());
			transaction.setWeeks(restore.getLoan().getWeeks());
			transaction.setBalancePartEnc(cryptoService.encrypt(Double.toString(rewardAmount), lenderKey));
			transactionService.getTransactionRepo().save(transaction);
		} else if (restore.getShippingStatus().getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER_2ND)) {
			Fine fine = new Fine();
			fine.setOwner(gamer);
			fine.setAmountEnc(cryptoService.encrypt(Double.toString(restore.getLoan().getPublicUserGame().getGame().getUploadPayment()), gamerKey));
			fine.setDescription(restore.getShippingStatus().getName());
			fineService.getFineRepo().save(fine);
		} else if (restore.getShippingStatus().getCode().equals(Code.SHIPPING_DELIVERED)) {
			PublicUserGame publicUserGame = restore.getPublicUserGame();
			publicUserGame.setIsBorrowed(false);
			publicUserService.getPublicUserGameRepo().save(publicUserGame);
			restore.setPublicUserGame(publicUserGame);
		}

		restoreRepo.save(restore);
		RestoreLite restoreLite = restoreRepo.findById(restore.getId());

		return restoreLite;
	}

	@Transactional
	public Loan confirmRestore(Restore restore, boolean isGamer) throws ServletException {
		Restore previous = restoreRepo.findOne(restore.getId());
		restore.setLoan(previous.getLoan());

		if (isGamer) {
			restore.setGamerConfirmDate(new Date());
		} else {
			restore.setLenderConfirmDate(new Date());
		}
		
		if (!restore.getUpdateDate().equals(previous.getUpdateDate())) {
			if (isGamer) {
				previous.setGamerAddress(restore.getGamerAddress());
				previous.setGamerGeolocation(restore.getGamerGeolocation());
				previous.setGamerReceiver(restore.getGamerReceiver());
			} else {
				previous.setLenderAddress(restore.getLenderAddress());
				previous.setLenderGeolocation(restore.getLenderGeolocation());
				previous.setLenderReceiver(restore.getLenderReceiver());
			}
			
			previous = restoreRepo.save(previous);
			return loanService.getLoanRepo().findOne(previous.getLoan().getId());
		}

		restore = restoreRepo.save(restore);
		return loanService.getLoanRepo().findOne(restore.getLoan().getId());
	}

	public RestoreRepo getRestoreRepo() {
		return restoreRepo;
	}
}
