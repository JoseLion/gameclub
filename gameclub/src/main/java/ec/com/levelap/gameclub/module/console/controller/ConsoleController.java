package ec.com.levelap.gameclub.module.console.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.gameclub.module.console.entity.Console;
import ec.com.levelap.gameclub.module.console.service.ConsoleService;

@RestController
@RequestMapping(value="api/console", produces=MediaType.APPLICATION_JSON_VALUE)
public class ConsoleController {
	@Autowired
	private ConsoleService consoleService;
	
	@RequestMapping(value="findAll", method=RequestMethod.GET)
	public ResponseEntity<List<Console>> findAll() throws ServletException {
		List<Console> consoles = consoleService.getConsoleRepo().findAllByOrderByName();
		return new ResponseEntity<List<Console>>(consoles, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> save(@RequestPart Console console, @RequestPart(required=false) MultipartFile logo) throws ServletException, IOException {
		return consoleService.save(console, logo);
	}
	
	@Transactional
	@RequestMapping(value="changeStatus/{id}", method=RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatus(@PathVariable Long id) throws ServletException {
		Console console = consoleService.getConsoleRepo().findOne(id);
		console = consoleService.changeStatus(console);
		console = consoleService.getConsoleRepo().save(console);
		
		return new ResponseEntity<Boolean>(console.getStatus(), HttpStatus.OK);
	}
}
