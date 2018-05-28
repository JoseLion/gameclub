package ec.com.levelap.gameclub.module.avatar.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

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

import ec.com.levelap.gameclub.module.avatar.entity.Avatar;
import ec.com.levelap.gameclub.module.avatar.service.AvatarService;

@RestController
@RequestMapping(value = "api/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvatarController {

	@Autowired
	private AvatarService avatarService;

	@RequestMapping(value = "findAll", method = RequestMethod.GET)
	public ResponseEntity<?> findAll() throws ServletException {
		List<?> avatars = this.avatarService.getAvatarRepo().findByOrderByStatusDescCreationDateAsc();
		return new ResponseEntity<>(avatars, HttpStatus.OK);
	}

	@RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findOne(@PathVariable Long id) throws ServletException {
		Avatar avatar = this.avatarService.getAvatarRepo().findOne(id);
		return new ResponseEntity<>(avatar, HttpStatus.OK);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> save(@RequestPart MultipartFile image) throws ServletException, IOException {
		return this.avatarService.save(image);
	}

	@RequestMapping(value = "changeStatus/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> changeStatus(@PathVariable Long id) throws ServletException {
		Avatar avatar = this.avatarService.getAvatarRepo().findOne(id);
		avatar = this.avatarService.changeStatus(avatar);
		avatar = this.avatarService.getAvatarRepo().save(avatar);
		return new ResponseEntity<>(avatar.getStatus(), HttpStatus.OK);
	}

}
