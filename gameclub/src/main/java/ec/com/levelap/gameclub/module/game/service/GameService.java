package ec.com.levelap.gameclub.module.game.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.entity.FileData;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.service.DocumentService;
import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.module.game.repository.GameRepo;

@Service
public class GameService extends BaseService<Game> {
	public GameService() {
		super(Game.class);
	}
	
	@Autowired
	private GameRepo gameRepo;
	
	@Autowired
	private DocumentService documentService;
	
	@Transactional
	public ResponseEntity<?> save(Game game, MultipartFile cover, MultipartFile banner) throws ServletException, IOException {
		if (game.getId() == null) {
			Game found = gameRepo.findByName(game.getName());
			
			if (found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("El juego ingresado ya existe", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			Game original = gameRepo.findOne(game.getId());
			Game found = gameRepo.findByNameAndNameIsNot(game.getName(), original.getName());
			
			if (found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("El juego ingresado ya existe", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (cover != null) {
			Archive archive = new Archive();
			
			if (game.getId() != null) {
				Game original = gameRepo.findOne(game.getId());
				documentService.deleteFile(original.getCover().getName(), Game.class.getSimpleName());
				archive = original.getCover();
			}
			
			FileData fileData = documentService.saveFile(cover, Game.class.getSimpleName());
			
			archive.setModule(Game.class.getSimpleName());
			archive.setName(fileData.getName());
			archive.setType(cover.getContentType());
			game.setCover(archive);
		}
		
		if (banner != null) {
			Archive archive = new Archive();
			
			if (game.getId() != null) {
				Game original = gameRepo.findOne(game.getId());
				documentService.deleteFile(original.getBanner().getName(), Game.class.getSimpleName());
				archive = original.getBanner();
			}
			
			FileData fileData = documentService.saveFile(banner, Game.class.getSimpleName());
			
			archive.setModule(Game.class.getSimpleName());
			archive.setName(fileData.getName());
			archive.setType(banner.getContentType());
			game.setBanner(archive);
		}
		
		gameRepo.save(game);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public GameRepo getGameRepo() {
		return gameRepo;
	}
}
