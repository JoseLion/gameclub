package ec.com.levelap.gameclub.module.message.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.service.MessageService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/message", produces=MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value="findMessages", method=RequestMethod.POST)
	public ResponseEntity<Page<Message>> findMessages(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<Message> messages = messageService.getMessageRepo().findMessages(search.text, new PageRequest(search.page, Const.TABLE_SIZE));
		return new ResponseEntity<Page<Message>>(messages, HttpStatus.OK);
	}
	
	private static class Search {
		public String text = "";
		
		public Integer page = 0;
	}
}
