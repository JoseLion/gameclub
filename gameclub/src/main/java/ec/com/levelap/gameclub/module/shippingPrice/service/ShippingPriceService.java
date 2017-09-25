package ec.com.levelap.gameclub.module.shippingPrice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.commons.location.Location;
import ec.com.levelap.commons.location.LocationRepo;
import ec.com.levelap.gameclub.module.shippingPrice.entity.ShippingPrice;
import ec.com.levelap.gameclub.module.shippingPrice.repository.ShippingPriceRepo;
import ec.com.levelap.gameclub.utils.Code;
import ec.com.levelap.gameclub.utils.Const;

@Service
public class ShippingPriceService {
	@Autowired
	private ShippingPriceRepo shippingPriceRepo;
	
	@Autowired
	private LocationRepo locationRepo;

	public ShippingPriceRepo getShippingPriceRepo() {
		return shippingPriceRepo;
	}
	
	@Transactional
	public ResponseEntity<?> updatePrices(MultipartFile file) throws ServletException, IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		
		if (workbook.getNumberOfSheets() < 1 || workbook.getNumberOfSheets() > 1) {
			return new ResponseEntity<ErrorControl>(new ErrorControl("El archivo debe tener una sola hoja de trabajo", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		for (int j = 0; j < 3; j++) {
			for (int i = 5; i < sheet.getLastRowNum(); i++) {
				XSSFCell yCode = sheet.getRow(i).getCell(j);
				XSSFCell xCode = sheet.getRow(j).getCell(i);
				
				if (!yCode.getRawValue().equalsIgnoreCase(xCode.getRawValue())) {
				String message = "Formato inválido";
					
					if (j == 0) {
						message = "Los códigos de la columna 'A' no coinciden con los de la fila '1'";
					}
					
					if (j == 1) {
						message = "Las ciudades en la columna 'B' no coinciden con las de la fila '2'";
					}
					
					if (j == 2) {
						message = "Las provincias en la columna 'C' no coinciden con las de la fila '3'";
					}
					
					return new ResponseEntity<ErrorControl>(new ErrorControl(message, true), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		
		List<Location> provinces = locationRepo.findByParentCode(Code.COUNTRY_CODE);
		for (int i = 5; i < sheet.getLastRowNum(); i++) {
			String province = sheet.getRow(i).getCell(2).getStringCellValue();
			String city = sheet.getRow(i).getCell(1).getStringCellValue();
			
			if (getLocation(provinces, province, city) == null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("La ciudad '" + city + "' en la provincia '" + province + "' no existe en la base de datos actual", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		shippingPriceRepo.deleteAllInBatch();
		List<ShippingPrice> newPrices = new ArrayList<>();
		
		for (int i = 5; i < sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			String originProvince = row.getCell(2).getStringCellValue();
			String originCity = row.getCell(1).getStringCellValue();
			
			for (int j = 5; j < row.getLastCellNum(); j++) {
				try {
					XSSFCell cell = row.getCell(j);
					String destProvince = sheet.getRow(2).getCell(j).getStringCellValue();
					String destCity = sheet.getRow(1).getCell(j).getStringCellValue();
					
					ShippingPrice shippingPrice = new ShippingPrice();
					shippingPrice.setOrigin(getLocation(provinces, originProvince, originCity));
					shippingPrice.setDestination(getLocation(provinces, destProvince, destCity));
					shippingPrice.setCost(cell.getNumericCellValue());
					
					newPrices.add(shippingPrice);
					
					if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
						shippingPrice.getOrigin().setOther(row.getCell(0).getStringCellValue());
					} else {
						shippingPrice.getOrigin().setOther("" + row.getCell(0).getNumericCellValue());
					}
					
					if (sheet.getRow(0).getCell(j).getCellType() == Cell.CELL_TYPE_STRING) {
						shippingPrice.getDestination().setOther(sheet.getRow(0).getCell(j).getStringCellValue());
					} else {
						shippingPrice.getDestination().setOther("" + sheet.getRow(0).getCell(j).getNumericCellValue());
					}
				} catch (Exception e) {
					return new ResponseEntity<ErrorControl>(new ErrorControl("Formato de celda incorrecto. Las provincias y ciudades deben ser de tipo texto y los precios de tipo número", true), HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
			}
		}
		
		shippingPriceRepo.save(newPrices);
		
		Page<ShippingPrice> shippingPrices = shippingPriceRepo.findShippingPrices("", "", null, new PageRequest(0, Const.TABLE_SIZE));
		return new ResponseEntity<Page<ShippingPrice>>(shippingPrices, HttpStatus.OK);
	}
	
	private Location getLocation(List<Location> provinces, String provinceName, String cityName) {
		for (Location province : provinces) {
			if (province.getName().equalsIgnoreCase(provinceName)) {
				for (Location city : province.getChildren()) {
					if (city.getName().equalsIgnoreCase(cityName)) {
						return city;
					}
				}
			}
		}
		
		return null;
	}
}
