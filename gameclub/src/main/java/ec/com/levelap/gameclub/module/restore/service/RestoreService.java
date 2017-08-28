package ec.com.levelap.gameclub.module.restore.service;

import java.util.Date;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.restore.entity.Restore;
import ec.com.levelap.gameclub.module.restore.entity.RestoreLite;
import ec.com.levelap.gameclub.module.restore.repository.RestoreRepo;

@Service
public class RestoreService {
	@Autowired
	private RestoreRepo restoreRepo;
	
	@Autowired
	private MessageService messageService;
	
	@Transactional
	public RestoreLite save(Restore restore) throws ServletException {
		Restore previous = restoreRepo.findOne(restore.getId());
		
		if (!restore.getShippingStatus().equals(previous.getShippingStatus()) || (restore.getShippingNote() != null && !restore.getShippingNote().equalsIgnoreCase(previous.getShippingNote()))) {
			restore.setLenderStatusDate(new Date());
			restore.setGamerStatusDate(new Date());
			
			restore.getLenderMessage().setRead(false);
			restore.getGamerMessage().setRead(false);
			messageService.getMessageRepo().save(restore.getLenderMessage());
			messageService.getMessageRepo().save(restore.getGamerMessage());
		}
		
		restoreRepo.save(restore);
		RestoreLite restoreLite = restoreRepo.findById(restore.getId());
		
		return restoreLite;
	}

	public RestoreRepo getRestoreRepo() {
		return restoreRepo;
	}
}
