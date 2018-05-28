package ec.com.levelap.gameclub.module.avatar.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.avatar.service.AvatarService;

@RestController
@RequestMapping(value = "open/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvatarOpenController {

	@Autowired
	private AvatarService avatarService;

	@RequestMapping(value = "findAll", method = RequestMethod.GET)
	public ResponseEntity<?> findAll() throws ServletException {
		List<?> avatars = this.avatarService.getAvatarRepo().findByStatusIsTrueOrderByStatusDescCreationDateAsc();
		return new ResponseEntity<>(avatars, HttpStatus.OK);
	}

}
