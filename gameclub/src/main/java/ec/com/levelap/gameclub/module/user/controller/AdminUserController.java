package ec.com.levelap.gameclub.module.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.user.entity.AdminUser;
import ec.com.levelap.gameclub.module.user.entity.AdminUserLite;
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
	
	@RequestMapping(value="findAdminUsers", method=RequestMethod.POST)
	public ResponseEntity<List<AdminUserLite>> findAdminUsers(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		List<AdminUserLite> adminUsers = adminUserService.getAdminUserRepo().findAdminUsers(search.fullName, search.profileId, search.status, search.startDate, search.endDate);
		return new ResponseEntity<List<AdminUserLite>>(adminUsers, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<AdminUser> findOne(@PathVariable Long id) throws ServletException {
		AdminUser adminUser = adminUserService.getAdminUserRepo().findOne(id);
		return new ResponseEntity<AdminUser>(adminUser, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody AdminUser adminUser) throws ServletException {
		return adminUserService.save(adminUser);
	}
	
	@Transactional
	@RequestMapping(value="changeStatus/{id}", method=RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatus(@PathVariable Long id) throws ServletException {
		AdminUser adminUser = adminUserService.getAdminUserRepo().findOne(id);
		adminUser = adminUserService.changeStatus(adminUser);
		adminUser = adminUserService.getAdminUserRepo().save(adminUser);
		
		return new ResponseEntity<Boolean>(adminUser.getStatus(), HttpStatus.OK);
	}
	
	@RequestMapping(value="resetPassword/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> resetPassword(@PathVariable Long id) throws ServletException {
		adminUserService.resetPassword(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private static class Search {
		public String fullName = "";
		
		public Long profileId;
		
		public Boolean status;
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
}
