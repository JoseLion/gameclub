package ec.com.levelap.gameclub.module.game.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.module.game.entity.GameOpen;
import ec.com.levelap.gameclub.module.game.service.GameService;
import ec.com.levelap.gameclub.module.tcc.entity.LocationPrice;
import ec.com.levelap.gameclub.module.tcc.service.LocationPriceService;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="open/game", produces=MediaType.APPLICATION_JSON_VALUE)
public class GameOpenController {
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private LocationPriceService locationPriceService;
	
	@RequestMapping(value="findGames", method=RequestMethod.POST)
	public ResponseEntity<Page<GameOpen>> findGames(@RequestBody(required=false) Search search) throws ServletException {
		if (search == null) {
			search = new Search();
		}
		
		Page<GameOpen> games = gameService.getGameRepo().findGamesOpen(search.name, search.categoryId, search.consoleId, new PageRequest(search.page, Const.TABLE_SIZE));
		
		for (GameOpen gameOpen : games.getContent()) {
			Game game = gameService.getGameRepo().findOne(gameOpen.getId());
			gameOpen.setCategories(game.getCategories());
			gameOpen.setConsoles(game.getConsoles());
		}
		
		return new ResponseEntity<Page<GameOpen>>(games, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<Game> findOne(@PathVariable Long id) throws ServletException {
		Game game = gameService.getGameRepo().findOne(id);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
	@RequestMapping(value="findGamesByCategory/{categoryId}", method=RequestMethod.GET)
	public ResponseEntity<List<GameOpen>> findGamesByCategory(@PathVariable Long categoryId) throws ServletException {
		List<GameOpen> games = gameService.getGameRepo().findByCategoriesCategoryIdOrderByName(categoryId);
		return new ResponseEntity<List<GameOpen>>(games, HttpStatus.OK);
	}
	
	@RequestMapping(value="findAutocomplete/{name}", method=RequestMethod.GET)
	public ResponseEntity<?> findAutocomplete(@PathVariable String name) throws ServletException {
		return new ResponseEntity<>(this.gameService.getGameRepo().findAutocomplete(name), HttpStatus.OK);
	}
	
	@RequestMapping(value="getAvailableGames", method=RequestMethod.POST)
	public ResponseEntity<Page<PublicUserGame>> getAvailableGames(@RequestBody Filter filter) throws ServletException {
		PublicUser currentUser = publicUserService.getCurrentUser();
		PageRequest page = filter.sort.isEmpty() ? new PageRequest(0, 10 * (filter.page + 1)) : new PageRequest(0, 10 * (filter.page + 1), new Sort(filter.desc ? Direction.DESC : Direction.ASC, filter.sort));
		Page<PublicUserGame> games = gameService.getPublicUserGameRepo().findAvailableGames(currentUser, filter.gameId, filter.consoleId, page);
		
		if (currentUser != null && currentUser.getLocation() != null) {
			List<Long> destinationIds = new ArrayList<>();
			for (PublicUserGame cross : games.getContent()) {
				destinationIds.add(cross.getPublicUser().getLocation().getId());
			}
			
			List<LocationPrice> shippingCosts = locationPriceService.getLocationPriceRepo().findByOriginIdAndDestinationIdIn(currentUser.getLocation().getId(), destinationIds);
			for (int i = 0; i < games.getContent().size(); i++) {
				games.getContent().get(i).setShippingCost(shippingCosts.get(i).getCost());
			}
		}
		
		return new ResponseEntity<Page<PublicUserGame>>(games, HttpStatus.OK);
	}
	
	private static class Search {
		public String name = "";
		
		public Long categoryId;
		
		public Long consoleId;
		
		public Integer page = 0;
	}
	
	private static class Filter {
		public Long gameId;
		
		public Long consoleId;
		
		public String sort = "";
		
		public Boolean desc = true;
		
		public Integer page = 0;
	}
}
