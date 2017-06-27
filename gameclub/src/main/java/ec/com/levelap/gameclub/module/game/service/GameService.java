package ec.com.levelap.gameclub.module.game.service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.entity.FileData;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.commons.archive.Archive;
import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.commons.catalog.CatalogRepo;
import ec.com.levelap.commons.service.DocumentService;
import ec.com.levelap.gameclub.application.ApplicationContextHolder;
import ec.com.levelap.gameclub.module.category.entity.Category;
import ec.com.levelap.gameclub.module.category.repository.CategoryRepo;
import ec.com.levelap.gameclub.module.console.entity.Console;
import ec.com.levelap.gameclub.module.console.repository.ConsoleRepo;
import ec.com.levelap.gameclub.module.game.entity.ExcelReport;
import ec.com.levelap.gameclub.module.game.entity.Game;
import ec.com.levelap.gameclub.module.game.entity.GameCategory;
import ec.com.levelap.gameclub.module.game.entity.GameConsole;
import ec.com.levelap.gameclub.module.game.entity.GameMagazine;
import ec.com.levelap.gameclub.module.game.repository.GameRepo;
import ec.com.levelap.gameclub.utils.Code;

@Service
public class GameService extends BaseService<Game> {
	@Autowired
	private GameRepo gameRepo;
	
	@Autowired
	private CatalogRepo catalogRepo;
	
	@Autowired
	private ConsoleRepo consoleRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static List<String> headers;
	
	@Value("${priceCharting-configuration.url}")
	private String url;
	
	
	private static final String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	public GameService() {
		super(Game.class);
	}
	
	@Transactional
	public ResponseEntity<?> save(Game game, MultipartFile cover, MultipartFile banner, MultipartFile diamond) throws ServletException, IOException {
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
				
				if (original.getCover() != null) {
					documentService.deleteFile(original.getCover().getName(), Game.class.getSimpleName());
					archive = original.getCover();
				}
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
				
				if (original.getBanner() != null) {
					documentService.deleteFile(original.getBanner().getName(), Game.class.getSimpleName());
					archive = original.getBanner();
				}
			}
			
			FileData fileData = documentService.saveFile(banner, Game.class.getSimpleName());
			
			archive.setModule(Game.class.getSimpleName());
			archive.setName(fileData.getName());
			archive.setType(banner.getContentType());
			game.setBanner(archive);
		}
		
		if (banner != null) {
			Archive archive = new Archive();
			
			if (game.getId() != null) {
				Game original = gameRepo.findOne(game.getId());
				
				if (original.getDiamond() != null) {
					documentService.deleteFile(original.getDiamond().getName(), Game.class.getSimpleName());
					archive = original.getDiamond();
				}
			}
			
			FileData fileData = documentService.saveFile(diamond, Game.class.getSimpleName());
			
			archive.setModule(Game.class.getSimpleName());
			archive.setName(fileData.getName());
			archive.setType(diamond.getContentType());
			game.setDiamond(archive);
		}
		
		gameRepo.save(game);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Transactional
	public XSSFWorkbook getGamesTemplate() throws ServletException {
		updateHeaders();
		List<Catalog> contentRatings = catalogRepo.findByParentCode(Code.CONTENT_RATINGS);
		List<Console> consoles = consoleRepo.findAllByOrderByIdAsc();
		List<Category> categories = categoryRepo.findAllByOrderByIdAsc();
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet gamesSheet = workbook.createSheet("Juegos");
		XSSFRow headings = gamesSheet.createRow(0);
		XSSFCell cell;
		
		for (int i = 0; i < headers.size(); i++) {
			cell = headings.createCell(i, Cell.CELL_TYPE_STRING);
			setHeadingStyle(cell);
			cell.setCellValue(headers.get(i));
			gamesSheet.autoSizeColumn(i);
			
			switch (headers.get(i)) {
			case "Restriccion Contenido":
				addCommentToCell(cell, "ID de restriccion de contenido. Ejemplo: 4");
				break;
				
			case "*Consolas":
				addCommentToCell(cell, "IDs de consolas separados por comas. Ejemplo: 1,5,3");
				break;
				
			case "*Categorias":
				addCommentToCell(cell, "IDs de categorías separados por comas. Ejemplo: 2,7,1");
				break;

			default:
				break;
			}
		}
		
		XSSFRow ratingRow;
		int i = 0;
		int j = 1;
		
		XSSFSheet ratingsSheet = workbook.createSheet("IDs Restricción Contenido");
		headings = ratingsSheet.createRow(0);
		i = 0;
		
		cell = headings.createCell(i, Cell.CELL_TYPE_STRING);
		setHeadingStyle(cell);
		cell.setCellValue("ID");
		ratingsSheet.autoSizeColumn(i);
		i++;
		
		cell = headings.createCell(i, Cell.CELL_TYPE_STRING);
		setHeadingStyle(cell);
		cell.setCellValue("ESRB");
		ratingsSheet.autoSizeColumn(i);
		i++;
		
		for (Catalog rating : contentRatings) {
			i = 0;
			ratingRow = ratingsSheet.createRow(j);
			
			cell = ratingRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(rating.getId());
			ratingsSheet.autoSizeColumn(i);
			i++;
			
			cell = ratingRow.createCell(i, Cell.CELL_TYPE_STRING);
			cell.setCellValue(rating.getName());
			ratingsSheet.autoSizeColumn(i);
			i++;
			
			j++;
		}
		
		
		XSSFSheet consolesSheet = workbook.createSheet("IDs Consolas");
		headings = consolesSheet.createRow(0);
		i = 0;
		
		cell = headings.createCell(i, Cell.CELL_TYPE_STRING);
		setHeadingStyle(cell);
		cell.setCellValue("ID");
		consolesSheet.autoSizeColumn(i);
		i++;
		
		cell = headings.createCell(i, Cell.CELL_TYPE_STRING);
		setHeadingStyle(cell);
		cell.setCellValue("Consola");
		consolesSheet.autoSizeColumn(i);
		i++;
		
		XSSFRow consoleRow;
		j = 1;
		
		for (Console console : consoles) {
			i = 0;
			consoleRow = consolesSheet.createRow(j);
			
			cell = consoleRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(console.getId());
			consolesSheet.autoSizeColumn(i);
			i++;
			
			cell = consoleRow.createCell(i, Cell.CELL_TYPE_STRING);
			cell.setCellValue(console.getName());
			consolesSheet.autoSizeColumn(i);
			i++;
			
			j++;
		}
		
		
		XSSFSheet categoriesSheet = workbook.createSheet("IDs Categoría");
		headings = categoriesSheet.createRow(0);
		i = 0;
		
		cell = headings.createCell(i, Cell.CELL_TYPE_STRING);
		setHeadingStyle(cell);
		cell.setCellValue("ID");
		categoriesSheet.autoSizeColumn(i);
		i++;
		
		cell = headings.createCell(i, Cell.CELL_TYPE_STRING);
		setHeadingStyle(cell);
		cell.setCellValue("Categoria");
		categoriesSheet.autoSizeColumn(i);
		i++;
		
		XSSFRow categoryRow;
		j = 1;
		
		for (Category category : categories) {
			i = 0;
			categoryRow = categoriesSheet.createRow(j);
			
			cell = categoryRow.createCell(i, Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(category.getId());
			consolesSheet.autoSizeColumn(i);
			i++;
			
			cell = categoryRow.createCell(i, Cell.CELL_TYPE_STRING);
			cell.setCellValue(category.getName());
			consolesSheet.autoSizeColumn(i);
			i++;
			
			j++;
		}
		
		return workbook;
	}
	
	@Transactional
	public ExcelReport processGamesExcel(XSSFWorkbook workbook) throws ServletException {
		updateHeaders();
		XSSFSheet sheet = workbook.getSheet("Juegos");
		
		if (sheet == null) {
			return new ExcelReport(false);
		}
		
		if (sheet.getRow(0) == null) {
			return new ExcelReport(false);
		}
		
		for (int i = 0; i < headers.size(); i++) {
			if (!headers.get(i).equals(sheet.getRow(0).getCell(i).getStringCellValue())) {
				return new ExcelReport(false);
			}
		}
		
		ExcelReport report = new ExcelReport(sheet.getLastRowNum());
		report.setHasFormat(true);
		report.setWrongRows(0);
		
		for (int j = 1; j <= sheet.getLastRowNum(); j++) {
			boolean rowHasError = false;
			
			for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
				XSSFCell cell = sheet.getRow(j).getCell(i);
				
				if (headers.get(i).contains("*")) {
					if (cell == null) {
						rowHasError = true;
						report.getErrors().put(chars[i] + (j+1), "Campo requerido");
					}
				}
				
				if (cell != null) {
					if (headers.get(i).toLowerCase().contains("*nombre")) {
						try {
							String name = cell.getStringCellValue();
							Game found = gameRepo.findByName(name);
							
							if (found != null) {
								rowHasError = true;
								report.getErrors().put(chars[i] + (j+1), "El juego con nombre \"" + name + "\" ya existe");
							}
						} catch (Exception e) {
							rowHasError = true;
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe ser de tipo texto");
						}
					}
					
					if (headers.get(i).toLowerCase().contains("fecha")) {
						try {
							cell.getDateCellValue();
						} catch (Exception e) {
							rowHasError = true;
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe ser de tipo fecha");
						}
					}
					
					if (headers.get(i).toLowerCase().contains("restriccion")) {
						try {
							if (cell.getRawValue() != null) {
								Long id = Long.parseLong(cell.getRawValue());
								Catalog contentRating = catalogRepo.findOne(id);
								
								if (contentRating == null) {
									rowHasError = true;
									report.getErrors().put(chars[i] + (j+1), "No se pudo encontrar el ID especificado");
								} else {
									if (contentRating.getParent() == null || !contentRating.getParent().getCode().equalsIgnoreCase(Code.CONTENT_RATINGS)) {
										rowHasError = true;
										report.getErrors().put(chars[i] + (j+1), "No se pudo encontrar el ID especificado");
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							rowHasError = true;
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe ser de tipo numérico");
						}
					}
					
					if (headers.get(i).toLowerCase().contains("rating")) {
						try {
							if (cell.getRawValue() != null) {
								Integer rating = (int)cell.getNumericCellValue();
								
								if (rating < 0 || rating > 100) {
									rowHasError = true;
									report.getErrors().put(chars[i] + (j+1), "Los rating deben ser valores solamente del 1 al 100");
								}
							}
						} catch (Exception e) {
							rowHasError = true;
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe ser de tipo numérico");
						}
					}
					
					if (headers.get(i).toLowerCase().contains("url")) {
						try {
							if (cell.getRawValue() != null) {
								String url = cell.getStringCellValue();
								
								if (!url.isEmpty()) {
									try {
										URL test = new URL(url);
										URLConnection conn = test.openConnection();
										
										if (conn.getContentType() == null) {
											rowHasError = true;
											report.getErrors().put(chars[i] + (j+1), "El URL ingresado no es válido");
										}
									} catch(Exception e) {
										rowHasError = true;
										report.getErrors().put(chars[i] + (j+1), "El URL ingresado no es válido");
									}
								}
							}
						} catch (Exception e) {
							rowHasError = true;
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe ser de tipo texto");
						}
					}
					
					if (headers.get(i).toLowerCase().contains("consolas") || headers.get(i).toLowerCase().contains("categorias")) {
						try {
							if (cell.getRawValue() != null) {
								String[] split;
								
								if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
									split = cell.getStringCellValue().trim().split(",");
								} else {
									split = cell.getRawValue().trim().split(",");
								}
								
								for (String text : split) {
									try {
										Long id = Long.parseLong(text);
										
										if (id <= 0) {
											rowHasError = true;
											report.getErrors().put(chars[i] + (j+1), "Los IDs no pueden ser menores o iguales a cero");
											break;
										} else {
											if (headers.get(i).toLowerCase().contains("consolas")) {
												Console console = consoleRepo.findOne(id);
												
												if (console == null) {
													rowHasError = true;
													report.getErrors().put(chars[i] + (j+1), "Uno o más IDs de consola no pudo ser encontrado");
													break;
												}
											}
											
											if (headers.get(i).toLowerCase().contains("categorias")) {
												Category category = categoryRepo.findOne(id);
												
												if (category == null) {
													rowHasError = true;
													report.getErrors().put(chars[i] + (j+1), "Uno o más IDs de categoría no pudo ser encontrado");
													break;
												}
											}
										}
									} catch (Exception e) {
										rowHasError = true;
										report.getErrors().put(chars[i] + (j+1), "Uno o más IDs no es un valor numérico");
									}
								}
							}
						} catch (Exception e) {
							rowHasError = true;
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe ser de tipo texto");
						}
					}
					
					if (headers.get(i).toLowerCase().contains("price")) {
						try {
							if (cell.getRawValue() != null) {
								Double number = cell.getNumericCellValue();
								
								if (number < 0) {
									rowHasError = true;
									report.getErrors().put(chars[i] + (j+1), "El valor debe ser mayor o igual a cero");
								} else {
									HashMap<String, String> priceChart = getPriceCharting("" + number.intValue());
									System.out.println("STATUS: " + priceChart.get("status"));
									
									if (priceChart.get("status") != null && priceChart.get("status").equals("error")) {
										if (priceChart.get("error_message").equals("No such product")) {
											report.getErrors().put(chars[i] + (j+1), "No se pudo encontrar el ID de Price Charting");
										} else {
											report.getErrors().put(chars[i] + (j+1), "Error en Price Charting: " + priceChart.get("error_message"));
										}
									}
								}
							}
						} catch (Exception e) {
							rowHasError = true;
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe ser de tipo numérico");
							e.printStackTrace();
						}
					}
				}
			}
			
			if (rowHasError) {
				report.setWrongRows(report.getWrongRows()+1);
			}
		}
		
		return report;
	}
	
	@Transactional
	public void saveBulkLoad(XSSFWorkbook workbook) throws ServletException {
		XSSFSheet sheet = workbook.getSheet("Juegos");
		List<Game> games = new ArrayList<>();
		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			Game game = new Game();
			int m = 0;
			
			game.setName(row.getCell(m).getStringCellValue());
			m++;
			game.setDescription(row.getCell(m).getStringCellValue());
			m++;
			game.setReleaseDate(row.getCell(m).getDateCellValue());
			m++;
			if (row.getCell(m) != null) {
				game.setContentRating(catalogRepo.findOne(Long.parseLong(row.getCell(m).getRawValue())));
			}
			
			m++;
			
			List<GameMagazine> gameMagazines = new ArrayList<>();
			List<Catalog> magazines = catalogRepo.findByParentCode(Code.MAGAZINES);
			for (Catalog magazine : magazines) {
				GameMagazine cross = new GameMagazine();
				cross.setGame(game);
				cross.setMagazine(magazine);
				cross.setRating((int)row.getCell(m).getNumericCellValue());
				m++;
				cross.setUrl(row.getCell(m).getStringCellValue());
				m++;
				
				gameMagazines.add(cross);
			}
			
			game.setMagazineRatings(gameMagazines);
			String[] split;
			
			if (row.getCell(m).getCellType() == Cell.CELL_TYPE_STRING) {
				split = row.getCell(m).getStringCellValue().trim().split(",");
			} else {
				split = row.getCell(m).getRawValue().trim().split(",");
			}
			
			List<GameConsole> gameConsoles = new ArrayList<>();
			for (String text : split) {
				GameConsole cross = new GameConsole();
				cross.setGame(game);
				cross.setConsole(consoleRepo.findOne(Long.parseLong(text)));
				gameConsoles.add(cross);
			}
			
			game.setConsoles(gameConsoles);
			m++;
			
			if (row.getCell(m).getCellType() == Cell.CELL_TYPE_STRING) {
				split = row.getCell(m).getStringCellValue().trim().split(",");
			} else {
				split = row.getCell(m).getRawValue().trim().split(",");
			}
			
			List<GameCategory> gameCategories = new ArrayList<>();
			for (String text : split) {
				GameCategory cross = new GameCategory();
				cross.setGame(game);
				cross.setCategory(categoryRepo.findOne(Long.parseLong(text)));
				gameCategories.add(cross);
			}
			
			game.setCategories(gameCategories);
			m++;
			
			Double id = row.getCell(m).getNumericCellValue();
			game.setPriceChartingId(id.longValue());
			game.setUploadPayment(getAvailablePrice(getPriceCharting("" + id.intValue())));
			m++;
			
			if (row.getCell(m) != null) {
				game.setTrailerUrl(row.getCell(m).getStringCellValue());
			}
			
			games.add(game);
		}
		
		gameRepo.save(games);
	}
	
	private void setHeadingStyle(XSSFCell cell) {
		XSSFCellStyle style = cell.getSheet().getWorkbook().createCellStyle();
		Font font = cell.getSheet().getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(font);
		
		cell.setCellStyle(style);
	}
	
	private void addCommentToCell(XSSFCell cell, String text) {
		CreationHelper factory = cell.getSheet().getWorkbook().getCreationHelper();
		Drawing drawing = cell.getSheet().createDrawingPatriarch();
		ClientAnchor anchor = factory.createClientAnchor();
		
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex()+2);
		anchor.setRow1(cell.getRow().getRowNum());
		anchor.setRow2(cell.getRow().getRowNum()+3);
		
		Comment comment = drawing.createCellComment(anchor);
		comment.setString(factory.createRichTextString(text));
		comment.setAuthor("Game Club");
		
		cell.setCellComment(comment);
	}
	
	private void updateHeaders() {
		CatalogRepo repo = ApplicationContextHolder.getContext().getBean(CatalogRepo.class);
		List<Catalog> magazines = repo.findByParentCode(Code.MAGAZINES);
		headers = new ArrayList<>();
		
		headers.add("*Nombre");
		headers.add("*Descripcion");
		headers.add("*Fecha Lanzamiento");
		headers.add("Restriccion Contenido");
		
		for (Catalog magazine : magazines) {
			headers.add("*Rating " + magazine.getName());
			headers.add("*URL " + magazine.getName());
		}
		
		headers.add("*Consolas");
		headers.add("*Categorias");
		headers.add("*ID Price Charting");
		headers.add("URL Trailer");
	}

	public GameRepo getGameRepo() {
		return gameRepo;
	}
	
	@Transactional
	public HashMap<String, String> getPriceCharting(String id) throws ServletException {
		restTemplate.setErrorHandler(new RestResponseHandler());
		this.checkConfiguration();
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://www.pricecharting.com/api/product");
		uriBuilder.queryParam("t", "bf03e53dcbc519e849eafba5189cc928dc139a65");
		uriBuilder.queryParam("id", id);
		//System.out.println("URI: " + uriBuilder.build().toUri());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> httpRequest = new HttpEntity<>(null, headers);
		
		ResponseEntity<HashMap<String, String>> response = restTemplate.exchange(uriBuilder.build().toUri(), HttpMethod.GET, httpRequest, new ParameterizedTypeReference<HashMap<String, String>>() {});
		System.out.println("********* TEST: " + response.getBody());
		
		return response.getBody();
	}
	
	public Double getAvailablePrice(HashMap<String, String> priceChart) {
		if (priceChart.get("gamestop-price") != null) {
			return Double.parseDouble(priceChart.get("gamestop-price")) / 100.0;
		}
		
		if (priceChart.get("retail-cib-buy") != null) {
			return Double.parseDouble(priceChart.get("retail-cib-buy")) / 100.0;
		}
		
		if (priceChart.get("retail-new-buy") != null) {
			return Double.parseDouble(priceChart.get("retail-new-buy")) / 100.0;
		}
		
		return null;
	}
	
	private void checkConfiguration() throws ServletException {
		if (url == null || url.equals("")) {
			throw new ServletException("Make sure you set up Price Charting Url into your application.properties or application.yml");
		}
	}
	
	private static class RestResponseHandler implements ResponseErrorHandler {
		@Override
		public boolean hasError(ClientHttpResponse response) throws IOException {
			if (response.getRawStatusCode() == 404) {
				return false;
			}
			
			return true;
		}

		@Override
		public void handleError(ClientHttpResponse response) throws IOException {
			
		}
	}
}
