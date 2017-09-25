package ec.com.levelap.gameclub.module.shippingPrice.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.gameclub.module.shippingPrice.entity.ShippingPrice;
import ec.com.levelap.gameclub.module.shippingPrice.repository.ShippingPriceRepo;

@Service
public class ShippingPriceService {
	@Autowired
	private ShippingPriceRepo shippingPriceRepo;

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
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
		}
	}
}
