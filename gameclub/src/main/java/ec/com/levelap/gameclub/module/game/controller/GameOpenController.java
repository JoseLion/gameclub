package ec.com.levelap.gameclub.module.game.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.category.entity.Category;
import ec.com.levelap.gameclub.module.console.entity.Console;
import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.module.game.entity.GameOpen;
import ec.com.levelap.gameclub.module.game.service.GameService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="open/game", produces=MediaType.APPLICATION_JSON_VALUE)
public class GameOpenController {
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value="findGames", method=RequestMethod.POST)
	public ResponseEntity<Page<GameOpen>> findGames(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<GameOpen> games = gameService.getGameRepo().findGamesOpen(search.name, search.category, search.console, new PageRequest(search.page, Const.TABLE_SIZE));
		
		for (GameOpen gameOpen : games.getContent()) {
			Game game = gameService.getGameRepo().findOne(gameOpen.getId());
			gameOpen.setCategories(game.getCategories());
			gameOpen.setConsoles(game.getConsoles());
		}
		
		return new ResponseEntity<Page<GameOpen>>(games, HttpStatus.OK);
	}
	
	@RequestMapping(value="findGamesByCategory/{categoryId}", method=RequestMethod.GET)
	public ResponseEntity<List<GameOpen>> findGamesByCategory(@PathVariable Long categoryId) throws ServletException {
		List<GameOpen> games = gameService.getGameRepo().findByCategoriesCategoryIdOrderByName(categoryId);
		return new ResponseEntity<List<GameOpen>>(games, HttpStatus.OK);
	}
	
	private static class Search {
		public String name = "";
		
		public Category category;
		
		public Console console;
		
		public Integer page = 0;
	}
}
