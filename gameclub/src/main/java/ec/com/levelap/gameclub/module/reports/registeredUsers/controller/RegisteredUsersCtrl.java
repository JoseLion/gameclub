package ec.com.levelap.gameclub.module.reports.registeredUsers.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.jasper.JasperService;
import ec.com.levelap.gameclub.module.reports.registeredUsers.entity.RegisteredUsers;
import ec.com.levelap.gameclub.module.reports.registeredUsers.repository.RegisteredUsersRepo;
import ec.com.levelap.gameclub.utils.Const;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value="api/report/registeredUsers", produces=MediaType.APPLICATION_JSON_VALUE)
public class RegisteredUsersCtrl {
	
	@Autowired
	private RegisteredUsersRepo registeredUsersRepo;
	
	@Autowired
	private JasperService jasperService;
	
	@RequestMapping(value="registeredUsersAll", method=RequestMethod.GET)
	public ResponseEntity<?> registeredUsersAll() throws ServletException, IOException, GeneralSecurityException {
		Page<RegisteredUsers> registeredUsersAll = registeredUsersRepo.registeredUsersPage(new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<>(registeredUsersAll, HttpStatus.OK);
	}
	
	@RequestMapping(value="totalUsers", method=RequestMethod.GET)
	public ResponseEntity<?> totalUsers() throws ServletException, IOException, GeneralSecurityException {
		Long totalUsers = registeredUsersRepo.totalUsers();
		return new ResponseEntity<>(totalUsers, HttpStatus.OK);
	}

	@RequestMapping(value="findRegisteredUsers", method=RequestMethod.POST)
	public ResponseEntity<List<RegisteredUsers>> findRegisteredUsers(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		List<RegisteredUsers> logPlatformGames = registeredUsersRepo.findRegisteredUsers(search.name, search.document, search.username, search.startDate, search.endDate);
		return new ResponseEntity<List<RegisteredUsers>>(logPlatformGames, HttpStatus.OK);
	}
	
	@RequestMapping(value="getExcelReport", method=RequestMethod.GET)
	public void getExcelReport(@RequestParam(required=false) Map<String, Object> params, HttpServletResponse response) throws ServletException, JRException, SQLException, IOException {
		File report = jasperService.createExcelReport("/jasper/registeredUsers.jrxml", params);
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"registered-users.xlsx\""));
		response.setContentLengthLong(report.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(report));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	@RequestMapping(value="getPdfReport", method=RequestMethod.GET)
	public void getPdfReport(@RequestParam(required=false) Map<String, Object> params, HttpServletResponse response) throws ServletException, JRException, SQLException, IOException {
		File report = jasperService.createPdfReport("/jasper/registeredUsers.jrxml", params);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"registered-users.pdf\""));
		response.setContentLengthLong(report.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(report));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	private static class Search {
		
		public String name = "";
		
		public String document = "";
		
		public String username = "";
		
		public Date startDate = new Date(0);
		
		public Date endDate = new Date();
	}
}
