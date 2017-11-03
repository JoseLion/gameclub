package ec.com.levelap.gameclub.module.restore.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.entity.RestoreLite;
import ec.com.levelap.gameclub.module.restore.repository.RestoreRepo;
import ec.com.levelap.gameclub.module.settings.service.SettingService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.kushki.KushkiException;
import ec.com.levelap.kushki.service.KushkiService;

@Service
public class RestoreService {
	@Autowired
	private RestoreRepo restoreRepo;

	@Autowired
	private LoanRepo loanRepo;

	@Autowired
	private SettingService settingService;

	@Autowired
	private PublicUserService publicUserService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private LevelapCryptography cryptoService;

	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private CatalogRepo catalogRepo;

	@Autowired
	private KushkiService kushkiService;

	@Transactional
	@SuppressWarnings("unchecked")
	public RestoreLite save(Restore restore)
			throws ServletException, GeneralSecurityException, IOException, KushkiException {
		Restore previous = restoreRepo.findOne(restore.getId());
		restore.setLoan(previous.getLoan());

		Catalog shippingStatus = catalogRepo.findByCode(restore.getShippingStatus().getCode());

		if (!shippingStatus.equals(previous.getShippingStatus()) || (restore.getShippingNote() != null
				&& !restore.getShippingNote().equalsIgnoreCase(previous.getShippingNote()))) {
			restore.setLenderStatusDate(new Date());
			restore.setGamerStatusDate(new Date());

			restore.getLenderMessage().setRead(false);
			restore.getGamerMessage().setRead(false);
			messageService.getMessageRepo().save(restore.getLenderMessage());
			messageService.getMessageRepo().save(restore.getGamerMessage());
		}

		PublicUser gamer = publicUserService.getPublicUserRepo().findOne(restore.getGamer().getId());
		File keyGamer = File.createTempFile("keyGamer", ".tmp");
		FileUtils.writeByteArrayToFile(keyGamer, gamer.getPrivateKey());

		PublicUser lender = publicUserService.getPublicUserRepo()
				.findOne(restore.getPublicUserGame().getPublicUser().getId());
		File keyLender = File.createTempFile("keyLender", ".tmp");
		FileUtils.writeByteArrayToFile(keyLender, lender.getPrivateKey());

		Transaction transaction;

		if (shippingStatus.getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER)) {
			Double value = Double.valueOf(settingService.getSettingValue(Code.SETTING_GAMER_DIDNT_DELIVER));

			gamer = publicUserService.substractFromUserBalance(restore.getGamer().getId(), value);
			Double totalBalanceGamer = gamer.getShownBalance() - value;
			byte[] toBalance = null;
			byte[] toCard = null;
			if (totalBalanceGamer < 0) {
				gamer = publicUserService.setUserBalance(gamer.getId(), 0D);
				Map<String, Object> kushkiSubscription = new HashMap<>();
				kushkiSubscription.put("amount", (Double) Math.abs(totalBalanceGamer));
				try {
					kushkiService.subscriptionCharge(restore.getLoan().getPayment().getSubscriptionId(),
							kushkiSubscription);
				} catch (KushkiException ex) {
					throw new KushkiException(ex);
				}
				toBalance = cryptoService.encrypt(Double.toString(gamer.getShownBalance()), keyGamer);
				toCard = cryptoService.encrypt(Double.toString(Math.abs(totalBalanceGamer)), keyGamer);
			} else {
				gamer = publicUserService.substractFromUserBalance(gamer.getId(), value);
				toBalance = cryptoService.encrypt(Double.toString(value), keyGamer);
			}

			lender = publicUserService.addToUserBalance(restore.getPublicUserGame().getPublicUser().getId(), value);

			transaction = new Transaction(gamer, "MULTA - " + shippingStatus.getName(),
					restore.getPublicUserGame().getGame().getName(), restore.getLoan().getWeeks(), null, toBalance,
					toCard);
			transactionRepo.save(transaction);

			transaction = new Transaction(lender, "DEVOLICION", restore.getPublicUserGame().getGame().getName(),
					restore.getLoan().getWeeks(), cryptoService.encrypt(Double.toString(value), keyLender), null, null);
			transactionRepo.save(transaction);

		} else if (shippingStatus.getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER_2ND)) {
			Double value = restore.getPublicUserGame().getGame().getUploadPayment();
			String priceCharting = settingService.getSettingValue(Code.SETTING_NATIONALIZACION);
			gamer = publicUserService.substractFromUserBalance(restore.getGamer().getId(),
					value + (value * Double.valueOf(priceCharting)));

			Double totalBalanceGamer = gamer.getShownBalance() - value + (value * Double.valueOf(priceCharting));
			byte[] toBalance = null;
			byte[] toCard = null;
			if (totalBalanceGamer < 0) {
				toBalance = cryptoService.encrypt(Double.toString(gamer.getShownBalance()), keyGamer);
				toCard = cryptoService.encrypt(Double.toString(Math.abs(totalBalanceGamer)), keyGamer);
				
				gamer = publicUserService.setUserBalance(gamer.getId(), 0D);
				Map<String, Object> kushkiSubscription = new HashMap<>();
				kushkiSubscription.put("amount", (Double) Math.abs(totalBalanceGamer));
				Loan loan = loanRepo.findOne(restore.getLoan().getId());
				
				try {
					kushkiService.subscriptionCharge(loan.getPayment().getSubscriptionId(), kushkiSubscription);
				} catch (KushkiException ex) {
					throw new KushkiException(ex);
				}
				
			} else {
				gamer = publicUserService.substractFromUserBalance(gamer.getId(), value);
				toBalance = cryptoService.encrypt(Double.toString(value), keyGamer);
			}

			transaction = new Transaction(gamer, "MULTA - " + shippingStatus.getName(),
					restore.getPublicUserGame().getGame().getName(), restore.getLoan().getWeeks(), null, toBalance,
					toCard);
			transactionRepo.save(transaction);
		} else if(shippingStatus.getCode().equals(Code.SHIPPING_DELIVERED)) {
			PublicUserGame publicUserGame = restore.getPublicUserGame();
			publicUserGame.setIsBorrowed(Boolean.TRUE);
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

		restore = restoreRepo.save(restore);
		return loanRepo.findOne(restore.getLoan().getId());
	}

	public RestoreRepo getRestoreRepo() {
		return restoreRepo;
	}
}
