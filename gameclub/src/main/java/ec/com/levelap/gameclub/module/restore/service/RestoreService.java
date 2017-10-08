package ec.com.levelap.gameclub.module.restore.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.repository.FineRepo;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.repository.LoanRepo;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.entity.RestoreLite;
import ec.com.levelap.gameclub.module.restore.repository.RestoreRepo;
import ec.com.levelap.gameclub.module.settings.entity.Setting;
import ec.com.levelap.gameclub.module.settings.service.SettingService;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.kushki.KushkiException;
import ec.com.levelap.kushki.object.KushkiAmount;
import ec.com.levelap.kushki.service.KushkiService;

@Service
public class RestoreService {
	@Autowired
	private RestoreRepo restoreRepo;
	
	@Autowired
	private LoanRepo loanRepo;
	
	@Autowired
	private FineRepo fineRepo;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private KushkiService kushkiService;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public RestoreLite save(Restore restore) throws ServletException, GeneralSecurityException, IOException, KushkiException {
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
		
		if (restore.getShippingStatus().getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER)) {
			Setting setting = settingService.getSettingsRepo().findByCode(Code.SETTING_GAMER_DIDNT_DELIVER);
			Fine fine = new Fine();
			fine.setOwner(restore.getLoan().getGamer());
			fine.setDescription(restore.getShippingStatus().getName());
			
			if (setting.getType().equals(Const.SETTINGS_PERCENTAGE)) {
				fine.setAmount(restore.getLoan().getCost() * (Double.parseDouble(setting.getValue()) / 100.0));
			} else {
				fine.setAmount(Double.parseDouble(setting.getValue()));
			}
			
			publicUserService.addToUserBalance(restore.getLoan().getPublicUserGame().getPublicUser().getId(), fine.getAmount());
			fineRepo.save(fine);
		}
		
		if (restore.getShippingStatus().getCode().equals(Code.SHIPPING_GAMER_DIDNT_DELIVER_2ND)) {
			if (restore.getLoan().getGamer().getShownBalance() >= restore.getLoan().getPublicUserGame().getGame().getUploadPayment()) {
				publicUserService.substractFromUserBalance(restore.getLoan().getGamer().getId(), restore.getLoan().getPublicUserGame().getGame().getUploadPayment());
			} else {
				Double diff = restore.getLoan().getPublicUserGame().getGame().getUploadPayment() - restore.getLoan().getGamer().getShownBalance();
				publicUserService.substractFromUserBalance(restore.getLoan().getGamer().getId(), restore.getLoan().getGamer().getShownBalance());
				
				Map<String, Object> optionals = new HashMap<>();
				optionals.put("amount", new KushkiAmount(diff));
				kushkiService.subscriptionCharge(restore.getLoan().getGamer().getPaymentMethods().get(0).getSubscriptionId(), optionals);
			}
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
