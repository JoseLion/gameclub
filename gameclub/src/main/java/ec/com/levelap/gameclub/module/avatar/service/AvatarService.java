package ec.com.levelap.gameclub.module.avatar.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.archive.Archive;
import ec.com.levelap.archive.ArchiveService;
import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.entity.FileData;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.avatar.entity.Avatar;
import ec.com.levelap.gameclub.module.avatar.repository.AvatarRepo;

@Service
public class AvatarService extends BaseService<Avatar> {

	public AvatarService() {
		super(Avatar.class);
	}

	@Autowired
	private AvatarRepo avatarRepo;

	@Autowired
	private ArchiveService archiveService;

	@Transactional
	public ResponseEntity<?> save(MultipartFile image) throws ServletException, IOException {
		if (image == null) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("Debe subir una imagen con el avatar", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Archive archive = new Archive();
		FileData fileData = archiveService.saveFile(image, Avatar.class.getSimpleName());
		archive.setModule(Avatar.class.getSimpleName());
		archive.setName(fileData.getName());
		archive.setType(image.getContentType());
		Avatar avatar = new Avatar();
		avatar.setImage(archive);
		avatar = this.avatarRepo.save(avatar);
		return new ResponseEntity<>(avatar, HttpStatus.OK);
	}

	public AvatarRepo getAvatarRepo() {
		return avatarRepo;
	}

}
