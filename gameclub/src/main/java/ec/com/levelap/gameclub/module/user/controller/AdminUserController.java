package ec.com.levelap.gameclub.module.user.controller;

import java.util.HashMap;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.user.entity.AdminUser;
import ec.com.levelap.gameclub.module.user.service.AdminUserService;

@RestController
@RequestMapping(value="api/adminUser", produces=MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController {
	@Autowired
	private AdminUserService adminUserService;
	
	@RequestMapping(value="getCurrentUser", method=RequestMethod.GET)
	public ResponseEntity<AdminUser> getCurrentUser() throws ServletException {
		AdminUser adminUser = adminUserService.getCurrentUser();
		return new ResponseEntity<AdminUser>(adminUser, HttpStatus.OK);
	}
	
	@RequestMapping(value="changePassword", method=RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestBody HashMap<String, String> passworMap) throws ServletException {
		return adminUserService.changePassword(passworMap);
	}
}
