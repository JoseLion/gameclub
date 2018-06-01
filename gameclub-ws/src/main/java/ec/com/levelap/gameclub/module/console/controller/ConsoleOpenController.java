package ec.com.levelap.gameclub.module.console.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.console.entity.Console;
import ec.com.levelap.gameclub.module.console.service.ConsoleService;

@RestController
@RequestMapping(value="open/console", produces=MediaType.APPLICATION_JSON_VALUE)
public class ConsoleOpenController {
	@Autowired
	private ConsoleService consoleService;
	
	@RequestMapping(value="findAll", method=RequestMethod.GET)
	public ResponseEntity<List<Console>> findAll() throws ServletException {
		List<Console> consoles = consoleService.getConsoleRepo().findByStatusIsTrueOrderByName();
		return new ResponseEntity<List<Console>>(consoles, HttpStatus.OK);
	}
}
