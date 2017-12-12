package ec.com.levelap.gameclub.module.process.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.process.entity.StatusProcess;
import ec.com.levelap.gameclub.module.process.repository.StatusProcessRepo;

@RestController
@RequestMapping(value = "api/statusProcess", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusProcessController {

	@Autowired
	private StatusProcessRepo statusProcessRepo;

	@RequestMapping(value = "findByFrom/{code}", method = RequestMethod.GET)
	public ResponseEntity<?> findByFrom(@PathVariable String code) throws ServletException {
		List<StatusProcess> statusProcesses = statusProcessRepo.findByStatusFromCodeAndStatusIsTrue(code);
		return new ResponseEntity<>(statusProcesses, HttpStatus.OK);
	}

}
