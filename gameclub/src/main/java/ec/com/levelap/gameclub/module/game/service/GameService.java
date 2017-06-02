package ec.com.levelap.gameclub.module.game.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.CellType;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	private static List<String> headers = new ArrayList<>();
	
	private static final String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	public GameService() {
		super(Game.class);
		
		CatalogRepo repo = ApplicationContextHolder.getContext().getBean(CatalogRepo.class);
		List<Catalog> magazines = repo.findByParentCode(Code.MAGAZINES);
		
		headers.add("*Nombre");
		headers.add("*Descripcion");
		headers.add("*Fecha Lanzamiento");
		headers.add("Restriccion Contenido");
		
		for (Catalog magazine : magazines) {
			headers.add("*Rating " + magazine.getName());
			headers.add("*URL " + magazine.getName());
		}
		
		headers.add("*Consolas");
		headers.add("*Categorías");
		headers.add("*Costo Promedio / Semana");
		headers.add("*Coins por Cargar");
		headers.add("URL Trailer");
	}
	
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
	
	@Transactional
	public XSSFWorkbook getGamesTemplate() throws ServletException {
		List<Catalog> contentRatings = catalogRepo.findByParentCode(Code.CONTENT_RATINGS);
		List<Console> consoles = consoleRepo.findAllByOrderByIdAsc();
		List<Category> categories = categoryRepo.findAllByOrderByIdAsc();
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet gamesSheet = workbook.createSheet("Juegos");
		XSSFRow headings = gamesSheet.createRow(0);
		XSSFCell cell;
		
		for (int i = 0; i < headers.size(); i++) {
			cell = headings.createCell(i, CellType.STRING);
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
				
			case "*Categorías":
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
		
		cell = headings.createCell(i, CellType.STRING);
		setHeadingStyle(cell);
		cell.setCellValue("ID");
		ratingsSheet.autoSizeColumn(i);
		i++;
		
		cell = headings.createCell(i, CellType.STRING);
		setHeadingStyle(cell);
		cell.setCellValue("ESRB");
		ratingsSheet.autoSizeColumn(i);
		i++;
		
		for (Catalog rating : contentRatings) {
			i = 0;
			ratingRow = ratingsSheet.createRow(j);
			
			cell = ratingRow.createCell(i, CellType.NUMERIC);
			cell.setCellValue(rating.getId());
			ratingsSheet.autoSizeColumn(i);
			i++;
			
			cell = ratingRow.createCell(i, CellType.STRING);
			cell.setCellValue(rating.getName());
			ratingsSheet.autoSizeColumn(i);
			i++;
			
			j++;
		}
		
		
		XSSFSheet consolesSheet = workbook.createSheet("IDs Consolas");
		headings = consolesSheet.createRow(0);
		i = 0;
		
		cell = headings.createCell(i, CellType.STRING);
		setHeadingStyle(cell);
		cell.setCellValue("ID");
		consolesSheet.autoSizeColumn(i);
		i++;
		
		cell = headings.createCell(i, CellType.STRING);
		setHeadingStyle(cell);
		cell.setCellValue("Consola");
		consolesSheet.autoSizeColumn(i);
		i++;
		
		XSSFRow consoleRow;
		j = 1;
		
		for (Console console : consoles) {
			i = 0;
			consoleRow = consolesSheet.createRow(j);
			
			cell = consoleRow.createCell(i, CellType.NUMERIC);
			cell.setCellValue(console.getId());
			consolesSheet.autoSizeColumn(i);
			i++;
			
			cell = consoleRow.createCell(i, CellType.STRING);
			cell.setCellValue(console.getName());
			consolesSheet.autoSizeColumn(i);
			i++;
			
			j++;
		}
		
		
		XSSFSheet categoriesSheet = workbook.createSheet("IDs Categoría");
		headings = categoriesSheet.createRow(0);
		i = 0;
		
		cell = headings.createCell(i, CellType.STRING);
		setHeadingStyle(cell);
		cell.setCellValue("ID");
		categoriesSheet.autoSizeColumn(i);
		i++;
		
		cell = headings.createCell(i, CellType.STRING);
		setHeadingStyle(cell);
		cell.setCellValue("Categoria");
		categoriesSheet.autoSizeColumn(i);
		i++;
		
		XSSFRow categoryRow;
		j = 1;
		
		for (Category category : categories) {
			i = 0;
			categoryRow = categoriesSheet.createRow(j);
			
			cell = categoryRow.createCell(i, CellType.NUMERIC);
			cell.setCellValue(category.getId());
			consolesSheet.autoSizeColumn(i);
			i++;
			
			cell = categoryRow.createCell(i, CellType.STRING);
			cell.setCellValue(category.getName());
			consolesSheet.autoSizeColumn(i);
			i++;
			
			j++;
		}
		
		return workbook;
	}
	
	@SuppressWarnings("deprecation")
	@Transactional
	public ExcelReport processGamesExcel(XSSFWorkbook workbook) throws ServletException {
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
					if (headers.get(i).toLowerCase().contains("fecha")) {
						try {
							cell.getDateCellValue();
						} catch (Exception e) {
							rowHasError = true;
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe der de tipo fecha");
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
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe der de tipo numérico");
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
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe der de tipo numérico");
						}
					}
					
					if (headers.get(i).toLowerCase().contains("url")) {
						try {
							if (cell.getRawValue() != null) {
								String url = cell.getStringCellValue();
								
								if (!url.isEmpty()) {
									try {
										URL test = new URL(url);
										
										if (test.getContent() == null) {
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
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe der de tipo texto");
						}
					}
					
					if (headers.get(i).toLowerCase().contains("consolas") || headers.get(i).toLowerCase().contains("categorias")) {
						try {
							if (cell.getRawValue() != null) {
								String[] split;
								
								if (cell.getCellType() == 0) {
									split = cell.getRawValue().trim().split(",");
								} else {
									split = cell.getStringCellValue().trim().split(",");
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
												System.out.println("Console ID: " + id);
												
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
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe der de tipo texto");
						}
					}
					
					if (headers.get(i).toLowerCase().contains("costo") || headers.get(i).toLowerCase().contains("coins")) {
						try {
							if (cell.getRawValue() != null) {
								Double number = Double.parseDouble(cell.getRawValue());
								
								if (number < 0) {
									rowHasError = true;
									report.getErrors().put(chars[i] + (j+1), "El valor debe ser mayor o igual a cero");
								}
							}
						} catch (Exception e) {
							rowHasError = true;
							report.getErrors().put(chars[i] + (j+1), "Formato de celda debe der de tipo numérico");
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
	
	private void setHeadingStyle(XSSFCell cell) {
		XSSFCellStyle style = cell.getSheet().getWorkbook().createCellStyle();
		Font font = cell.getSheet().getWorkbook().createFont();
		font.setBold(true);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(font);
		
		cell.setCellStyle(style);
	}
	
	private void addCommentToCell(XSSFCell cell, String text) {
		CreationHelper factory = cell.getSheet().getWorkbook().getCreationHelper();
		Drawing drawing = cell.getSheet().createDrawingPatriarch();
		ClientAnchor anchor = factory.createClientAnchor();
		
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex()+1);
		anchor.setRow1(cell.getRow().getRowNum());
		anchor.setRow2(cell.getRow().getRowNum()+3);
		
		Comment comment = drawing.createCellComment(anchor);
		comment.setString(factory.createRichTextString(text));
		comment.setAuthor("Game Club");
		
		cell.setCellComment(comment);
	}

	public GameRepo getGameRepo() {
		return gameRepo;
	}
}
