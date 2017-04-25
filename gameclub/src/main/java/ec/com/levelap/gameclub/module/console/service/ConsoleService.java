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
import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.entity.FileData;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.base.service.DocumentService;
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
	public DocumentService documentService;
	
	@Transactional
	public ResponseEntity<?> save(Console console, MultipartFile logo) throws ServletException, IOException {
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
		
		if (logo != null) {
			Archive archive = new Archive();
			
			if (console.getId() != null) {
				Console original = consoleRepo.findOne(console.getId());
				documentService.deleteFile(Console.class.getSimpleName(), original.getLogo().getName());
				archive = original.getLogo();
			}
			
			FileData fileData = documentService.saveFile(logo, Console.class.getSimpleName());
			
			archive.setPath(fileData.getPath());
			archive.setName(fileData.getName());
			archive.setType(logo.getContentType());
			console.setLogo(archive);
		}
		
		consoleRepo.save(console);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ConsoleRepo getConsoleRepo() {
		return consoleRepo;
	}
}
