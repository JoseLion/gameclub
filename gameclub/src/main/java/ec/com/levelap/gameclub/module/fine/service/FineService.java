package ec.com.levelap.gameclub.module.fine.service;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.repository.FineRepo;
import ec.com.levelap.gameclub.module.kushki.entity.KushkiSubscription;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.repository.TransactionRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;
import ec.com.levelap.kushki.service.KushkiService;

@Service
public class FineService extends BaseService<Fine> {
	public FineService() {
		super(Fine.class);
	}

	@Autowired
	private FineRepo fineRepo;

	@Autowired
	private PublicUserService publicUserService;

	@Autowired
	private KushkiService kushkiService;

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private LevelapCryptography cryptoService;

	@Autowired
	private TransactionRepo transactionRepo;

	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> save(Fine fineObj, Boolean isApply)
			throws ServletException, IOException, GeneralSecurityException {
		fineObj = fineRepo.findOne(fineObj.getId());
		fineObj.setApply(isApply);
		if (isApply) {
			PublicUser publicUser = publicUserService.getPublicUserRepo().findOne(fineObj.getOwner().getId());
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());

			fineObj.setOwner(publicUser);
			fineObj.setWasPayed(Boolean.TRUE);

			Double totalBalance = publicUser.getShownBalance() - fineObj.getAmount();
			if (totalBalance < 0) {
				totalBalance = Math.abs(totalBalance);

				fineObj.setCardPartEnc(cryptoService.encrypt(Double.toString(totalBalance), key));
				fineObj.setBalancePartEnc(publicUser.getBalance());

				Map<String, Object> kushkiSubscription = new HashMap<>();
				kushkiSubscription.put("amount", fineObj.getCardPart());
				for (KushkiSubscription subscription : publicUser.getPaymentMethods()) {
					try {
						kushkiService.subscriptionCharge(subscription.getSubscriptionId(), kushkiSubscription);
						break;
					} catch (KushkiException ex) {
						fineObj.setWasPayed(Boolean.FALSE);
						fineRepo.save(fineObj);
						return new ResponseEntity<>(new ErrorControl(ex.getMessage()),
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				publicUser = publicUserService.setUserBalance(publicUser.getId(), 0D);
			} else {
				fineObj.setCardPartEnc(null);
				fineObj.setBalancePartEnc(fineObj.getAmountEnc());
				publicUser = publicUserService.substractFromUserBalance(publicUser.getId(), fineObj.getAmount());
			}

			Transaction transaction = new Transaction(publicUser, "MULTA - ", "-", 0, null, fineObj.getBalancePartEnc(),
					fineObj.getCardPartEnc());
			transactionRepo.save(transaction);

			Message message = new Message();
			message = new Message();
			message.setIsLoan(Boolean.FALSE);
			message.setIsFine(Boolean.TRUE);
			message.setOwner(fineObj.getOwner());
			message.setDate(new Date());
			message.setSubject(Const.SBJ_FINE);
			message = messageRepo.save(message);

			fineObj.setMessage(message);
		}

		fineObj = fineRepo.save(fineObj);
		return new ResponseEntity<>(fineObj, HttpStatus.OK);
	}

	public FineRepo getFineRepo() {
		return fineRepo;
	}

	public PublicUserService getPublicUserService() {
		return publicUserService;
	}

}
