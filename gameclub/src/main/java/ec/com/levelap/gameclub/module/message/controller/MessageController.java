package ec.com.levelap.gameclub.module.message.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.amountRequest.entity.AmountRequest;
import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.loan.entity.Loan;
import ec.com.levelap.gameclub.module.loan.service.LoanService;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.module.welcomeKit.entity.WelcomeKit;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/message", produces=MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private LoanService loanService;
	
	@RequestMapping(value="findMessages", method=RequestMethod.POST)
	public ResponseEntity<Page<Message>> findMessages(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		PublicUser owner = publicUserService.getCurrentUser();
		Page<Message> messages = messageService.getMessageRepo().findMessages(owner, search.text, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<Message>>(messages, HttpStatus.OK);
	}
	
	@RequestMapping(value="getWelcomeKitMessages/{messageId}", method=RequestMethod.GET)
	public ResponseEntity<List<WelcomeKit>> getWelcomeKitMessages(@PathVariable Long messageId) throws ServletException {
		List<WelcomeKit> welcomeKits = messageService.getWelcomeKitRepo().findByMessageIdOrderByCreationDateDesc(messageId);
		Message message = messageService.getMessageRepo().findOne(messageId);
		message.setRead(true);
		messageService.getMessageRepo().save(message);
		return new ResponseEntity<List<WelcomeKit>>(welcomeKits, HttpStatus.OK);
	}
	
	@RequestMapping(value="getLoanMessage/{messageId}", method=RequestMethod.GET)
	public ResponseEntity<Loan> getLoanMessage(@PathVariable Long messageId) throws ServletException {
		Loan loan = loanService.getLoanRepo().findByGamerMessageIdOrLenderMessageId(messageId, messageId);
		Message message = messageService.getMessageRepo().findOne(messageId);
		message.setRead(true);
		messageService.getMessageRepo().save(message);
		return new ResponseEntity<Loan>(loan, HttpStatus.OK);
	}
	
	@RequestMapping(value="findFineMessage/{messageId}", method=RequestMethod.GET)
	public ResponseEntity<Fine> findFineMessage(@PathVariable Long messageId) throws ServletException{
		Fine fine = messageService.getMessageRepo().findFinesMessage(messageId);
		Message message = messageService.getMessageRepo().findOne(messageId);
		message.setRead(true);
		messageService.getMessageRepo().save(message);
		return new ResponseEntity<Fine>(fine, HttpStatus.OK);
	}
	
	@RequestMapping(value="amountRqFineMessage/{messageId}", method=RequestMethod.GET)
	public ResponseEntity<AmountRequest> amountRqFineMessage(@PathVariable Long messageId) throws ServletException{
		AmountRequest amountRequest = messageService.getMessageRepo().findAmountRqMessage(messageId);
		Message message = messageService.getMessageRepo().findOne(messageId);
		message.setRead(true);
		messageService.getMessageRepo().save(message);
		return new ResponseEntity<AmountRequest>(amountRequest, HttpStatus.OK);
	}
	
	
	private static class Search {
		public String text = "";
		
		public Integer page = 0;
	}
}
