package ec.com.levelap.gameclub.module.welcomeKit.service;

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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.paymentez.service.PaymentezService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.repository.PublicUserRepo;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKitLite;
import ec.com.levelap.gameclub.module.welcomeKit.repository.WelcomeKitRepo;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;

@Service
public class WelcomeKitService {

	@Autowired
	private WelcomeKitRepo welcomeKitRepo;

	@Autowired
	private CatalogRepo catalogRepo;

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private PublicUserRepo publicUserRepo;

	@Autowired
	private PublicUserService publicUserService;

	@Autowired
	private LevelapCryptography cryptoService;

	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private PaymentezService paymentezService;

	@Transactional
	public WelcomeKit confirmWelcomeKit(WelcomeKit welcomeKit) throws ServletException {
		Catalog shippingStatus = catalogRepo.findByCode(Code.SHIPPING_NO_TRACKING);
		welcomeKit.setShippingStatus(shippingStatus);
		welcomeKit.setWasConfirmed(true);
		welcomeKit.setConfirmationDate(new Date());
		welcomeKit = welcomeKitRepo.save(welcomeKit);
		return welcomeKit;
	}

	@Transactional
	public WelcomeKitLite save(WelcomeKit kit) throws ServletException {
		WelcomeKit previous = welcomeKitRepo.findOne(kit.getId());

		if (!kit.getShippingStatus().equals(previous.getShippingStatus()) || (kit.getShippingNote() != null && !kit.getShippingNote().equalsIgnoreCase(previous.getShippingNote()))) {
			Message message = messageRepo.findOne(kit.getMessage().getId());
			message.setRead(Boolean.FALSE);
			message = messageRepo.save(message);
			kit.setMessage(message);
		}

		if (kit.getShippingStatus().getCode().equals(Code.SHIPPING_DELIVERED)) {
			PublicUser publicUser = publicUserRepo.findOne(kit.getPublicUser().getId());
			publicUser.setIsReady(true); 
			publicUser = publicUserRepo.save(publicUser);
			kit.setPublicUser(publicUser);
		}

		kit = welcomeKitRepo.save(kit);
		WelcomeKitLite kitLite = welcomeKitRepo.findById(kit.getId());
		return kitLite;
	}

	@Transactional
	public PublicUser saveShippingKit(Integer quantity, Double amountBalance, Double amountCard, String cardReference)
			throws ServletException, IOException, GeneralSecurityException {
		PublicUser publicUser = publicUserService.getCurrentUser();
		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());

		Message message = new Message();
		message.setIsLoan(false);
		message.setOwner(publicUser);
		message.setSubject(Const.SBJ_SHIPPING_KIT);
		message.setIsShippingKit(Boolean.TRUE);
		messageRepo.save(message);

		WelcomeKit welcomeKit = new WelcomeKit();
		welcomeKit.setPublicUser(publicUser);
		welcomeKit.setMessage(message);
		welcomeKit.setQuantity(quantity);
		welcomeKit.setAmountBalance(cryptoService.encrypt(Double.toString(amountBalance), key));
		welcomeKit.setAmountCard(cryptoService.encrypt(Double.toString(amountCard), key));
		welcomeKit.setCardReference(cardReference);
		welcomeKitRepo.save(welcomeKit);

		return publicUserService.getCurrentUser();
	}

	@Transactional
	public WelcomeKit confirmShippingKit(WelcomeKit shippingKit) throws ServletException {
		Catalog shippingStatus = catalogRepo.findByCode(Code.SHIPPING_NO_TRACKING);
		WelcomeKit finalShippingKit = welcomeKitRepo.findOne(shippingKit.getId());
		finalShippingKit.setShippingStatus(shippingStatus);
		finalShippingKit.setWasConfirmed(Boolean.TRUE);
		finalShippingKit.setConfirmationDate(new Date());
		finalShippingKit.setAddress(shippingKit.getAddress());
		finalShippingKit.setGeolocation(shippingKit.getGeolocation());
		finalShippingKit.setPhone(shippingKit.getPhone());
		finalShippingKit.setReceiver(shippingKit.getReceiver());
		return welcomeKitRepo.save(finalShippingKit);
	}

	@Transactional
	public WelcomeKitLite sendShippingKit(Long shippingKitId, String tracking, String shippingNote, HttpSession session, HttpServletRequest request) throws ServletException, IOException, NumberFormatException, GeneralSecurityException, RestClientException, URISyntaxException, JSONException {
		WelcomeKit shippingKit = welcomeKitRepo.findOne(shippingKitId);
		PublicUser publicUser = publicUserService.getPublicUserRepo().findOne(shippingKit.getPublicUser().getId());

		File key = File.createTempFile("key", ".tmp");
		FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());

		Double amountBalance = shippingKit.getAmountBalanceValue();
		Double amountCard = shippingKit.getAmountCardValue();
		Double balance = Double.parseDouble(cryptoService.decrypt(publicUser.getBalance(), key));
		Double totalBalance = balance - amountBalance;

		if (totalBalance < 0) {
			amountCard += Math.abs(totalBalance);
			amountBalance = balance;
			balance = 0D;
			shippingKit.setAmountBalance(cryptoService.encrypt(Double.toString(amountBalance), key));
			shippingKit.setAmountCard(cryptoService.encrypt(Double.toString(amountCard), key));
		} else {
			balance = totalBalance;
		}

		if (amountCard > 0) {
			String description = "Shipping Kits de GameClub (" + shippingKit.getQuantity() + ")";
			String response = paymentezService.debitFromCard(session, request.getRemoteAddr(), shippingKit.getCardReference(), amountCard, 0.0, description);
			JSONObject json = new JSONObject(response);
			shippingKit.setTransactionId(json.getString("transaction_id"));
		}

		Catalog shippingStatus = catalogRepo.findByCode(Code.SHIPPING_DELIVERED);

		shippingKit.setShippingStatus(shippingStatus);
		shippingKit.setTracking(tracking);
		shippingKit.setShippingNote(shippingNote);
		shippingKit = welcomeKitRepo.save(shippingKit);

		Message message = messageRepo.findOne(shippingKit.getMessage().getId());
		message.setRead(Boolean.FALSE);
		messageRepo.save(message);

		publicUser = publicUserService.setUserBalance(publicUser.getId(), balance);

		Transaction transaction = new Transaction(
				publicUser,
				"SHIPPING KIT",
				"-","-",
				0,
				null,
				shippingKit.getAmountBalance(),
				shippingKit.getAmountCard());
		transactionRepo.save(transaction);

		return welcomeKitRepo.findById(shippingKit.getId());
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}

}
