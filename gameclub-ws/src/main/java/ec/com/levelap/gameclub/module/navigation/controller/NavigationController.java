package ec.com.levelap.gameclub.module.navigation.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.navigation.entity.Navigation;
import ec.com.levelap.gameclub.module.navigation.service.NavigationService;

@RestController
@RequestMapping(value="api/navigation", produces=MediaType.APPLICATION_JSON_VALUE)
public class NavigationController {
	@Autowired
	private NavigationService navigationService;
	
	@RequestMapping(value="findAll", method=RequestMethod.GET)
	public ResponseEntity<List<Navigation>> findAll() throws ServletException {
		List<Navigation> navigationArray = navigationService.getNavigationRepo().findByParentIsNull();
		return new ResponseEntity<List<Navigation>>(navigationArray, HttpStatus.OK);
	}
}
