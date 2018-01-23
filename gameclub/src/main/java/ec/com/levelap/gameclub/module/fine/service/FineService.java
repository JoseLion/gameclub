package ec.com.levelap.gameclub.module.fine.service;

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
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.cryptography.LevelapCryptography;
import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.repository.FineRepo;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.paymentez.service.PaymentezService;
import ec.com.levelap.gameclub.module.transaction.entity.Transaction;
import ec.com.levelap.gameclub.module.transaction.service.TransactionService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;

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
	private MessageRepo messageRepo;

	@Autowired
	private LevelapCryptography cryptoService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private PaymentezService paymentezService;

	@Transactional
	public ResponseEntity<?> save(Fine fine, Boolean isApply, HttpSession session, HttpServletRequest request) throws ServletException, IOException, GeneralSecurityException, RestClientException, URISyntaxException, JSONException {
		fine = fineRepo.findOne(fine.getId());
		fine.setApply(isApply);
		if (isApply) {
			PublicUser publicUser = publicUserService.getPublicUserRepo().findOne(fine.getOwner().getId());
			File key = File.createTempFile("key", ".tmp");
			FileUtils.writeByteArrayToFile(key, publicUser.getPrivateKey());

			fine.setOwner(publicUser);
			fine.setWasPayed(Boolean.TRUE);

			Double totalBalance = Double.parseDouble(cryptoService.decrypt(publicUser.getBalance(), key)) - fine.getAmount();
			
			if (totalBalance < 0) {
				totalBalance = (-1D) * (totalBalance);
				fine.setCardPartEnc(cryptoService.encrypt(Double.toString(totalBalance), key));
				fine.setBalancePartEnc(publicUser.getBalance());
				
				String response = paymentezService.listCurrentUserCards(session);
				JSONArray jsonArray = new JSONArray(response);
				paymentezService.debitFromCard(session, request.getRemoteAddr(), jsonArray.getJSONObject(0).getString("card_reference"), totalBalance/*fine.getCardPart()*/, 0.0, "Multa GameClub - " + fine.getDescription());
				
				publicUser = publicUserService.setUserBalance(publicUser.getId(), 0D);
				/************************************************************************/
			} else {
				fine.setCardPartEnc(null);
				fine.setBalancePartEnc(fine.getAmountEnc());
				publicUser = publicUserService.substractFromUserBalance(publicUser.getId(), fine.getAmount());
			}

			Transaction transaction = new Transaction(publicUser, "MULTA", "-", "-", 0, null, fine.getBalancePartEnc(),
					fine.getCardPartEnc());
			transactionService.getTransactionRepo().save(transaction);

			Message message = new Message();
			message = new Message();
			message.setIsLoan(Boolean.FALSE);
			message.setIsFine(Boolean.TRUE);
			message.setOwner(fine.getOwner());
			message.setDate(new Date());
			message.setSubject(Const.SBJ_FINE);
			message = messageRepo.save(message);

			fine.setMessage(message);
		}

		fine = fineRepo.save(fine);
		return new ResponseEntity<>(fine, HttpStatus.OK);
	}

	public FineRepo getFineRepo() {
		return fineRepo;
	}
}
