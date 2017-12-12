package ec.com.levelap.gameclub.module.fine.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.service.FineService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;

@RestController
@RequestMapping(value = "api/fine", produces = MediaType.APPLICATION_JSON_VALUE)
public class FineController {

	@Autowired
	private FineService fineService;

	@RequestMapping(value = "findFines", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Fine>> findFinesFilter(@RequestBody(required = false) Search search)
			throws ServletException {
		if (search == null) {
			search = new Search();
		}

		List<Fine> fines = fineService.getFineRepo().findFines(search.name, search.apply, search.wasPayed,
				search.startDate, search.endDate);
		return new ResponseEntity<List<Fine>>(fines, HttpStatus.OK);
	}

	@RequestMapping(value = "notApplyFine", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody Fine fineObj, HttpSession session, HttpServletRequest request) throws ServletException, RestClientException, IOException, GeneralSecurityException, URISyntaxException, JSONException {
		return fineService.save(fineObj, Boolean.FALSE, session, request);
	}

	@RequestMapping(value = "applyFine", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveFineBalance(@RequestBody Fine fineObj, HttpSession session, HttpServletRequest request) throws ServletException, RestClientException, IOException, GeneralSecurityException, URISyntaxException, JSONException {
		return fineService.save(fineObj, Boolean.TRUE, session, request);
	}

	@RequestMapping(value = "findFinesMessages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Fine>> findFinesMessages(PublicUser owner) throws ServletException, IOException {
		List<Fine> fines = fineService.getFineRepo().findFinesMessages(owner);
		return new ResponseEntity<List<Fine>>(fines, HttpStatus.OK);
	}

	private static class Search {
		public String name = "";

		public Boolean apply;

		public Boolean wasPayed;

		public Date startDate = new Date(0);

		public Date endDate = new Date();
	}
}