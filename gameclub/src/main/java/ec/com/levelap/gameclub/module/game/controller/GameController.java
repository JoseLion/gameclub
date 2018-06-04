package ec.com.levelap.gameclub.module.game.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.gameclub.module.category.entity.Category;
import ec.com.levelap.gameclub.module.console.entity.Console;
import ec.com.levelap.gameclub.module.game.entity.ExcelReport;
import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.module.game.entity.GameLite;
import ec.com.levelap.gameclub.module.game.service.GameService;
import ec.com.levelap.gameclub.utils.Const;

@RestController
@RequestMapping(value="api/game", produces=MediaType.APPLICATION_JSON_VALUE)
public class GameController {
	@Autowired
	private GameService gameService;
	
	@RequestMapping(value="findGames", method=RequestMethod.POST)
	public ResponseEntity<Page<GameLite>> findGames(@RequestBody(required=false) Search search) throws ServletException, ParseException {
		if(search == null) {
			search = new Search();
		}
		
		Page<GameLite> games = gameService.getGameRepo().findGames(search.name, search.releaseStart, search.releaseEnd, search.status, search.console, search.category, new PageRequest(search.page, Const.TABLE_SIZE));
		
		for (GameLite gameLite : games.getContent()) {
			Game game = gameService.getGameRepo().findOne(gameLite.getId());
			gameLite.setConsoles(game.getConsoles());
			gameLite.setCategories(game.getCategories());
		}
		
		return new ResponseEntity<Page<GameLite>>(games, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<Game> findOne(@PathVariable Long id) throws ServletException {
		Game game = gameService.getGameRepo().findOne(id);
		return new ResponseEntity<Game>(game, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> save(@RequestPart Game game, @RequestPart(required=false) MultipartFile cover, @RequestPart(required=false) MultipartFile banner, @RequestPart(required=false) MultipartFile diamond) throws ServletException, IOException {
		return gameService.save(game, cover, banner, diamond);
	}
	
	@RequestMapping(value="changeStatus/{id}", method=RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatus(@PathVariable Long id) throws ServletException {
		Game game = gameService.getGameRepo().findOne(id);
		game = gameService.changeStatus(game);
		game = gameService.getGameRepo().save(game);
		
		return new ResponseEntity<Boolean>(game.getStatus(), HttpStatus.OK);
	}
	
	@RequestMapping(value="downloadGamesTemplate", method=RequestMethod.POST)
	public void downloadGamesTemplate(HttpServletResponse response) throws ServletException, IOException {
		Workbook workbook = gameService.getGamesTemplate();
		response.setContentType("application/vnd.ms-excel");
		workbook.write(response.getOutputStream());
		response.flushBuffer();
	}
	
	@RequestMapping(value="processGamesExcel", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ExcelReport> processGamesExcel(@RequestPart("bulkloadFile") MultipartFile bulkloadFile) throws ServletException, IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(bulkloadFile.getInputStream());
		ExcelReport report = gameService.processGamesExcel(workbook);
		
		return new ResponseEntity<ExcelReport>(report, HttpStatus.OK);
	}
	
	@RequestMapping(value="saveBulkLoad", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> saveBulkLoad(@RequestPart("bulkloadFile") MultipartFile bulkloadFile) throws ServletException, IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(bulkloadFile.getInputStream());
		gameService.saveBulkLoad(workbook);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="getPriceCharting/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getPriceCharting(@PathVariable Long id) throws ServletException {
		Double price = null;
		if (id != null) {
			HashMap<String, String> response = gameService.getPriceCharting(id.toString());
			
			if (response == null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("No se pudo conectar con Price Charting", true), HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				price = gameService.getAvailablePrice(response);
				
				if (price != null) {
					String percentage = gameService.getGameRepo().priceChartinNationalitation();
					price = (double) Math.round(((price*Double.parseDouble(percentage)/100)+price)*100)/100;
					return new ResponseEntity<>(price, HttpStatus.OK);
				} else {
					return new ResponseEntity<ErrorControl>(new ErrorControl("No se encontró un precio en Price Charting. Por favor ingrese el precio manualmente", true), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		} else {
			return new ResponseEntity<ErrorControl>(new ErrorControl("ID no encontrado en el sistema", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@RequestMapping(value="reloadPrices", method=RequestMethod.GET)
	public ResponseEntity<?> reloadPrices() throws ServletException {
		gameService.reloadPrices();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private static class Search {
		public String name = "";
		
		public Date releaseStart = new Date(0);
		
		public Date releaseEnd = new Date(Const.POSTGRESQL_MAX_DATE);
		
		public Boolean status;
		
		public Console  console;
		
		public Category category;
		
		public Integer page = 0;
	}
}
