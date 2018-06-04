package ec.com.levelap.gameclub.module.console.service;

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
import ec.com.levelap.gameclub.module.console.entity.Console;
import ec.com.levelap.gameclub.module.console.repository.ConsoleRepo;

@Service
public class ConsoleService extends BaseService<Console> {
	public ConsoleService() {
		super(Console.class);
	}
	
	@Autowired
	private ConsoleRepo consoleRepo;
	
	@Autowired
	public ArchiveService archiveService;
	
	@Transactional
	public ResponseEntity<?> save(Console console, MultipartFile whiteLogo, MultipartFile blackLogo) throws ServletException, IOException {
		if (console.getId() == null) {
			Console found = consoleRepo.findByName(console.getName());
			
			if (found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("La consola ingresada ya existe", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			Console original = consoleRepo.findOne(console.getId());
			Console found = consoleRepo.findByNameAndNameIsNot(console.getName(), original.getName());
			
			if (found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("La consola ingresada ya existe", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (whiteLogo != null) {
			Archive archive = new Archive();
			
			if (console.getId() != null) {
				Console original = consoleRepo.findOne(console.getId());
				archiveService.deleteFile(original.getWhiteLogo().getName(), Console.class.getSimpleName());
				archive = original.getWhiteLogo();
			}
			
			FileData fileData = archiveService.saveFile(whiteLogo, Console.class.getSimpleName());
			
			archive.setModule(Console.class.getSimpleName());
			archive.setName(fileData.getName());
			archive.setType(whiteLogo.getContentType());
			console.setWhiteLogo(archive);
		}
		
		if (blackLogo != null) {
			Archive archive = new Archive();
			
			if (console.getId() != null) {
				Console original = consoleRepo.findOne(console.getId());
				archiveService.deleteFile(original.getBlackLogo().getName(), Console.class.getSimpleName());
				archive = original.getBlackLogo();
			}
			
			FileData fileData = archiveService.saveFile(blackLogo, Console.class.getSimpleName());
			
			archive.setModule(Console.class.getSimpleName());
			archive.setName(fileData.getName());
			archive.setType(blackLogo.getContentType());
			console.setBlackLogo(archive);
		}
		
		consoleRepo.save(console);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ConsoleRepo getConsoleRepo() {
		return consoleRepo;
	}
}
