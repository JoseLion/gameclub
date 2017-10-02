package ec.com.levelap.gameclub.module.settings.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ec.com.levelap.gameclub.module.settings.entity.Settings;
import ec.com.levelap.gameclub.module.settings.service.SettingsService;

import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping(value="api/settings", produces=MediaType.APPLICATION_JSON_VALUE)
public class SettingsController {
	@Autowired
	private SettingsService settingsService;
	
	@RequestMapping(value="findAll", method=RequestMethod.GET)
	public ResponseEntity<List<Settings>> findAll() throws ServletException{
		List<Settings> settingsList = settingsService.getSettingsRepo().findAll();
		return new ResponseEntity<List<Settings>>(settingsList, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody List<Settings> settingObj) throws ServletException, IOException{
		return settingsService.save(settingObj);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> findOne(@PathVariable Long id) throws ServletException{
		Settings settingObj = settingsService.getSettingsRepo().findOne(id);
		return new ResponseEntity<Settings>(settingObj, HttpStatus.OK);
	}
	
	@RequestMapping(value="listCategories", method=RequestMethod.GET)
	public ResponseEntity<List<SettingsCategories>> listSettingCategories() {
		List<SettingsCategories> setCat = new ArrayList<SettingsCategories>();
		List<Object> setCatPar = settingsService.getSettingsRepo().findSettingsCategories();
		for(Object obj : setCatPar) {
			setCat.add(new SettingsCategories(obj.toString()));
		}
		return new ResponseEntity<List<SettingsCategories>>(setCat, HttpStatus.OK);
	}
	
	@RequestMapping(value="priceChartings", method=RequestMethod.GET)
	public ResponseEntity<List<Settings>> priceChartings() {
		List<Settings> priceChartingList = settingsService.getSettingsRepo().findPriceCharting();
		return new ResponseEntity<List<Settings>>(priceChartingList, HttpStatus.OK);
	}
	
	private class SettingsCategories{
		@SuppressWarnings("unused")
		public String category;
		
		public SettingsCategories(String categoriesList) {
			this.category = categoriesList;
		}
	}
}
